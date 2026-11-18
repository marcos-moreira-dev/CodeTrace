package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PracticeEligibilityTest {
    @Test
    void onlyKnownProgrammingLanguagesAndMarkdownArePracticeable() {
        for (String filename : java.util.List.of("A.java", "index.ts", "App.tsx",
                "tool.py", "program.c", "hello.cpp", "main.go", "script.rb",
                "Program.cs", "build.ps1", "setup.sh", "run.cmd", "README.md"))
            assertTrue(PracticeEligibility.supports(filename), filename);
        for (String filename : java.util.List.of("image.png", "video.mp4",
                "archive.zip", "log.txt", "package.json", "unknown.binary", "pom.xml"))
            assertFalse(PracticeEligibility.supports(filename), filename);
    }
}
