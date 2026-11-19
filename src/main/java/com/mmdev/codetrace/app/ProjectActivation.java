package com.mmdev.codetrace.app;

import com.mmdev.codetrace.practice.PracticeMode;
import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.progress.SessionArchive;
import com.mmdev.codetrace.reference.GraphLayout;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A prepared project transition: fresh project or an existing .ctrace session.
 * No JavaFX controls, disk mutations, or background jobs live in this model.
 */
public record ProjectActivation(
        Path root, VirtualWorkspace workspace,
        Map<String, TypingSession> sessions, Map<String, String> hashes,
        PracticeMode practiceMode, Map<String, GraphLayout> graphLayouts,
        Path sessionFile) {

    public ProjectActivation {
        root = Objects.requireNonNull(root).toAbsolutePath().normalize();
        Objects.requireNonNull(workspace);
        Objects.requireNonNull(practiceMode);
        sessions = Collections.unmodifiableMap(new LinkedHashMap<>(
                Objects.requireNonNull(sessions)));
        hashes = Collections.unmodifiableMap(new LinkedHashMap<>(
                Objects.requireNonNull(hashes)));
        graphLayouts = Collections.unmodifiableMap(new LinkedHashMap<>(
                Objects.requireNonNull(graphLayouts)));
        if (sessionFile != null)
            sessionFile = sessionFile.toAbsolutePath().normalize();
    }

    public static ProjectActivation fresh(Path root) {
        return new ProjectActivation(root, new VirtualWorkspace(),
                Map.of(), Map.of(), PracticeMode.forProject(root), Map.of(), null);
    }

    /** Rebind a saved session to a user-selected reference folder if moved. */
    public static ProjectActivation resumed(Path selectedRoot, Path sessionFile,
                                             SessionArchive.State state) {
        Objects.requireNonNull(sessionFile);
        Objects.requireNonNull(state);
        return new ProjectActivation(selectedRoot, state.workspace(),
                state.sessions(), state.referenceHashes(), state.practiceMode(),
                state.graphs(), sessionFile);
    }
}
