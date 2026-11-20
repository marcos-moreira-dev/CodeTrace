package com.mmdev.codetrace.reference;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Set;
import java.util.TreeSet;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;

/** Folder exclusions affect the progress score only, never reference visibility.  * <p><strong>Engineering rationale.</strong> An exclusion removes files from aggregate progress but does not remove them from the reference or revoke read-only navigation. The representation must account for explicit exclusions and inherited folder rules without confusing them with file-system deletion.</p>
*/
public record ProgressExclusions(Set<String> folders) {
    private static final Preferences STORE =
            Preferences.userNodeForPackage(ProgressExclusions.class);

    public ProgressExclusions {
        if (folders == null) throw new IllegalArgumentException("Null exclusions");
        for (String f : folders) validate(f);
        folders = Set.copyOf(folders);
    }
    public static ProgressExclusions empty() { return new ProgressExclusions(Set.of()); }
    public boolean excludes(String relative) {
        if (relative == null) return false;
        return folders.stream().anyMatch(f ->
                relative.equals(f) || relative.startsWith(f + "/"));
    }
    public boolean explicitlyExcludes(String folder) { return folders.contains(folder); }
    public ProgressExclusions withFolder(String folder, boolean excluded) {
        validate(folder);
        Set<String> updated = new TreeSet<>(folders);
        if (excluded) updated.add(folder); else updated.remove(folder);
        return new ProgressExclusions(updated);
    }
    private static void validate(String path) {
        if (path == null || path.isBlank() || path.startsWith("/")
                || path.endsWith("/") || path.contains("\\"))
            throw new IllegalArgumentException("Invalid exclusion folder");
        for (String part : path.split("/", -1)) {
            if (part.isBlank() || part.equals(".") || part.equals(".."))
                throw new IllegalArgumentException("Invalid exclusion folder");
        }
    }
    public static ProgressExclusions load(Path root) {
        if (root == null) return empty();
        Set<String> entries = new TreeSet<>();
        for (String encoded : STORE.get(key(root), "").split(",")) {
            if (encoded.isBlank()) continue;
            try {
                String folder = new String(Base64.getUrlDecoder().decode(encoded),
                        StandardCharsets.UTF_8);
                validate(folder);
                entries.add(folder);
            } catch (IllegalArgumentException ignored) { /* old preference */ }
        }
        return new ProgressExclusions(entries);
    }
    public void save(Path root) {
        if (root == null) return;
        String serialized = folders.stream().sorted().map(f -> Base64.getUrlEncoder()
                .withoutPadding().encodeToString(f.getBytes(StandardCharsets.UTF_8)))
                .collect(Collectors.joining(","));
        if (serialized.length() > Preferences.MAX_VALUE_LENGTH)
            throw new IllegalArgumentException("Too many excluded folders");
        STORE.put(key(root), serialized);
    }
    private static String key(Path root) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256").digest(
                    root.toAbsolutePath().normalize().toString().getBytes(StandardCharsets.UTF_8));
            return "excluded-" + HexFormat.of().formatHex(digest, 0, 20);
        } catch (NoSuchAlgorithmException impossible) {
            throw new IllegalStateException(impossible);
        }
    }
}
