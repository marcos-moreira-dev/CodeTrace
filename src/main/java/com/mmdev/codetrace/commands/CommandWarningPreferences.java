package com.mmdev.codetrace.commands;

import java.util.prefs.Preferences;

/** Explicit user opt-out, defaulting to mandatory real-file execution warning.  * <p><strong>Engineering rationale.</strong> Confirmation choices for potentially destructive external commands form a separate safety policy from harmless informational notices. Suppression must remain reversible through Preferences and cannot weaken the fact that commands operate on original project files.</p>
*/
public final class CommandWarningPreferences {
    private static final Preferences STORE =
            Preferences.userNodeForPackage(CommandWarningPreferences.class);
    private static final String KEY = "confirmRealFileExecution";
    private CommandWarningPreferences() {}
    public static boolean mustConfirm() { return STORE.getBoolean(KEY, true); }
    public static void setMustConfirm(boolean enabled) { STORE.putBoolean(KEY, enabled); }
    public static void restore() { STORE.remove(KEY); }
}
