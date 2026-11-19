package com.mmdev.codetrace.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Owns document-tab identity and disposal, independently of application menus.
 * All closing paths (tab X, context menu, project change) release media and
 * Markdown resources exactly once and remove stale path registrations.
 */
public final class DocumentTabs {
    private final TabPane pane;
    private final Runnable changed;
    private final Map<String, Tab> byPath = new HashMap<>();

    public DocumentTabs(TabPane pane, Runnable changed) {
        this.pane = Objects.requireNonNull(pane);
        this.changed = Objects.requireNonNull(changed);
    }

    public Tab find(String path) {
        return byPath.get(path);
    }

    public String pathOf(Tab tab) {
        for (var entry : byPath.entrySet()) {
            if (entry.getValue() == tab) return entry.getKey();
        }
        return null;
    }

    /** Null path is allowed for transient views such as dependency maps. */
    public void open(String path, Tab tab) {
        Objects.requireNonNull(tab);
        if (path != null && byPath.containsKey(path)) {
            throw new IllegalStateException("Already opened: " + path);
        }
        if (path != null) byPath.put(path, tab);
        // JavaFX fires onClosed only for an X-button close, not for remove().
        tab.setOnClosed(event -> {
            disposeAndForget(tab);
            changed.run();
        });
        pane.getTabs().add(tab);
        pane.getSelectionModel().select(tab);
        changed.run();
    }

    public void close(Tab tab) {
        if (tab == null || !pane.getTabs().contains(tab)) return;
        disposeAndForget(tab);
        pane.getTabs().remove(tab);
        changed.run();
    }

    public void closeOthers(Tab keep) {
        if (keep == null || !pane.getTabs().contains(keep)) return;
        for (Tab tab : List.copyOf(pane.getTabs())) {
            if (tab != keep) {
                disposeAndForget(tab);
                pane.getTabs().remove(tab);
            }
        }
        pane.getSelectionModel().select(keep);
        changed.run();
    }

    public void closeAll() {
        for (Tab tab : List.copyOf(pane.getTabs())) disposeAndForget(tab);
        pane.getTabs().clear();
        byPath.clear();
        changed.run();
    }

    private void disposeAndForget(Tab tab) {
        byPath.values().removeIf(registered -> registered == tab);
        if (tab.getContent() instanceof ReferenceMediaPane media) media.dispose();
        if (tab.getContent() instanceof MarkdownPracticePane markdown) markdown.dispose();
        if (tab.getContent() instanceof ReadOnlyMarkdownPane reading) reading.dispose();
    }
}
