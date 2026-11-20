package com.mmdev.codetrace.practice;

import java.util.Locale;
import java.util.Objects;

/** Final, per-file transcription metrics; read only, not a project-wide score.  * <p><strong>Engineering rationale.</strong> A completion message reports exercise-level typing events and elapsed practice time, rather than pretending to certify that the original program compiles. Keeping summary calculation outside dialog construction makes success feedback testable without a JavaFX window.</p>
*/
public final class CompletionSummary {
    private CompletionSummary() {}

    /**
     * Average speed is correct visible graphemes divided by wall-clock
     * time since the first keystroke. Pauses are included, so this is an
     * approximate session average rather than instantaneous typing speed.
     */
    public static String message(String filename, TypingSession session,
                                 long elapsedMillis) {
        return message(filename, session, elapsedMillis, session.correctVisibleCount(), 0);
    }

    public static String message(String filename, TypingSession session,
                                 long elapsedMillis, long correctTypedEvents,
                                 long incorrectTypedEvents) {
        Objects.requireNonNull(filename);
        Objects.requireNonNull(session);
        int correct = session.correctVisibleCount();
        int visible = session.visibleUnitCount();
        int skipped = session.resolvedVisibleCount() - correct;
        long seconds = Math.max(0L, elapsedMillis / 1000L);
        String time = String.format(Locale.ROOT, "%02d:%02d:%02d",
                seconds / 3600, (seconds / 60) % 60, seconds % 60);
        String speed = elapsedMillis < 3000 || correctTypedEvents == 0
                ? "No calculable (práctica muy breve)"
                : Math.round(correctTypedEvents * 60_000.0 / elapsedMillis)
                    + " caracteres por minuto (CPM)";
        StringBuilder output = new StringBuilder();
        output.append("¡Bien hecho! Has completado «").append(filename).append("».\n\n")
                .append("Aciertos finales: ").append(correct).append(" de ").append(visible)
                .append("\nVelocidad media aproximada: ").append(speed)
                .append("\nTiempo transcurrido: ").append(time);
        if (incorrectTypedEvents > 0) {
            output.append("\nErrores al teclear durante esta práctica: ")
                    .append(incorrectTypedEvents);
        }
        if (skipped > 0) {
            output.append("\nCaracteres omitidos deliberadamente: ").append(skipped);
        }
        return output.toString();
    }
}
