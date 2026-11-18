package com.mmdev.codetrace.languages;

import java.util.List;

/** Syntax decoration only. Transcription and project rules are language-neutral. */
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
