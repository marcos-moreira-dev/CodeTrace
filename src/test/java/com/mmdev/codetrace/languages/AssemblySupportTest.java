package com.mmdev.codetrace.languages;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.reference.PracticeEligibility;
import com.mmdev.codetrace.reference.ProgressScope;
import org.junit.jupiter.api.Test;

class AssemblySupportTest {
    @Test void nasmIsTranscribableSourceWithRecognizableTokens() {
        var asm = LanguageRegistry.forFile("euclides.asm");
        assertEquals("NASM x86-64", asm.name());
        assertTrue(PracticeEligibility.supports("euclides.asm"));
        assertEquals(ProgressScope.Category.SOURCE, ProgressScope.classify("euclides.asm"));
        String code = "mov rax, 60 ; exit\nxor rdi, rdi\nsyscall\n";
        assertTrue(asm.highlight(code).size() >= 6);
    }
}
