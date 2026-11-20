package com.mmdev.codetrace.languages;

import java.util.List;

/** Syntax decoration only. Transcription and project rules are language-neutral.  * <p><strong>Engineering rationale.</strong> A language adapter describes how code can be displayed and practiced, not a compiler implementation. Keeping language rules behind this contract lets the core typing and virtual-filesystem models remain independent of Java, Python, C or other individual syntaxes.</p>
*/
public interface LanguageSupport {
    enum Kind { KEYWORD, TYPE, CALL, MEMBER, PROPERTY, SELECTOR, TAG, COLOR, COMMENT, STRING, NUMBER, ANNOTATION, HEADING }

    record Token(int start, int end, Kind kind) {
        public Token {
            if (start < 0 || end < start) {
                throw new IllegalArgumentException("Invalid source token range");
            }
        }
    }

    String name();

    boolean accepts(String filename);

    List<Token> highlight(String source);
}
