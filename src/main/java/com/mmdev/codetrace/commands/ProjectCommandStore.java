package com.mmdev.codetrace.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Project-specific commands, stored as a small versioned binary document
 * OUTSIDE the user's reference repository. These commands are not executable
 * through session loading and never run automatically.
 */
public final class ProjectCommandStore {
    private static final int MAGIC = 0x4354434D; // CTCM
    private static final int VERSION = 1;
    private static final int MAX_COMMANDS = 20;

    public enum Shell {
        CMD("CMD"),
        POWERSHELL("PowerShell"),
        SH("sh / Bash");

        private final String label;
        Shell(String label) { this.label = label; }
        public String label() { return label; }

        public static Shell preferred() {
            String os = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
            return os.contains("win") ? POWERSHELL : SH;
        }

        public List<String> invocation(String text) {
            return switch (this) {
                case CMD -> List.of("cmd.exe", "/d", "/c", text);
                case POWERSHELL -> List.of("powershell.exe", "-NoProfile", "-Command", text);
                case SH -> List.of("sh", "-c", text);
            };
        }
    }

    public record Preset(String name, Shell shell, String command, String directory) {
        public Preset {
            Objects.requireNonNull(name);
            Objects.requireNonNull(shell);
            Objects.requireNonNull(command);
            Objects.requireNonNull(directory);
            if (name.isBlank() || name.length() > 60
                    || command.isBlank() || command.length() > 3000
                    || directory.length() > 240) {
                throw new IllegalArgumentException("Invalid command preset");
            }
        }

        @Override
        public String toString() { return name; }
    }

    private final Path storageDirectory;

    public ProjectCommandStore() {
        this(Path.of(System.getProperty("user.home"), ".codetrace", "commands"));
    }

    public ProjectCommandStore(Path storageDirectory) {
        this.storageDirectory = Objects.requireNonNull(storageDirectory);
    }

    public List<Preset> load(Path projectRoot) throws IOException {
        Path source = storagePath(projectRoot);
        if (!Files.isRegularFile(source)) return List.of();
        if (Files.size(source) > 120_000) {
            throw new IOException("Command configuration file too large");
        }
        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(Files.newInputStream(source)))) {
            if (in.readInt() != MAGIC || in.readInt() != VERSION) {
                throw new IOException("Unsupported command file version");
            }
            int count = in.readInt();
            if (count < 0 || count > MAX_COMMANDS) throw new IOException("Invalid count");
            List<Preset> presets = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                presets.add(new Preset(in.readUTF(), Shell.valueOf(in.readUTF()),
                        in.readUTF(), in.readUTF()));
            }
            if (in.read() != -1)
                throw new IOException("Unexpected trailing command configuration data");
            return List.copyOf(presets);
        } catch (IllegalArgumentException malformed) {
            throw new IOException("Invalid command entry", malformed);
        }
    }

    public void save(Path projectRoot, List<Preset> presets) throws IOException {
        Objects.requireNonNull(presets);
        if (presets.size() > MAX_COMMANDS) {
            throw new IllegalArgumentException("At most 20 commands per project");
        }
        Files.createDirectories(storageDirectory);
        Path destination = storagePath(projectRoot);
        Path temp = Files.createTempFile(storageDirectory, ".commands-", ".tmp");
        try {
            try (DataOutputStream out = new DataOutputStream(
                    new BufferedOutputStream(Files.newOutputStream(temp)))) {
                out.writeInt(MAGIC);
                out.writeInt(VERSION);
                out.writeInt(presets.size());
                for (Preset preset : presets) {
                    out.writeUTF(preset.name());
                    out.writeUTF(preset.shell().name());
                    out.writeUTF(preset.command());
                    out.writeUTF(preset.directory());
                }
            }
            try {
                Files.move(temp, destination, StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException ignored) {
                Files.move(temp, destination, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temp);
        }
    }

    /** Working directories must remain within the explicitly selected project. */
    public static Path workingDirectory(Path projectRoot, Preset preset) throws IOException {
        Path root = projectRoot.toAbsolutePath().normalize();
        if (!Files.isDirectory(root)) throw new IOException("Reference folder not found");
        String requested = preset.directory().isBlank() ? "." : preset.directory();
        Path relative = Path.of(requested);
        if (relative.isAbsolute()) {
            throw new IOException("The command directory must be project-relative");
        }
        Path target = root.resolve(relative).normalize();
        if (!target.startsWith(root) || !Files.isDirectory(target)) {
            throw new IOException("Command directory is not inside the reference project");
        }
        // Never follow a symlink to run a command outside the chosen project.
        if (!target.toRealPath().startsWith(root.toRealPath())) {
            throw new IOException("Command directory resolves outside the project");
        }
        return target;
    }

    private Path storagePath(Path projectRoot) {
        Path absolute = Objects.requireNonNull(projectRoot).toAbsolutePath().normalize();
        String path = absolute.toString();
        String hex;
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                    .digest(path.getBytes(StandardCharsets.UTF_8));
            hex = java.util.HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException("SHA-256 not supported", impossible);
        }
        return storageDirectory.resolve(hex + ".ctcmd");
    }
}
