package com.mmdev.codetrace.practice;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class DependencyDeclarationRulesTest {
    @Test void recognizesLanguageSpecificDependenciesWithoutMatchingControlStatements() {
        assertTrue(DependencyDeclarationRules.isDeclaration("Main.java", "import java.util.List;"));
        assertTrue(DependencyDeclarationRules.isDeclaration("mod.py", "from pathlib import Path"));
        assertTrue(DependencyDeclarationRules.isDeclaration("mod.py", "import math"));
        assertTrue(DependencyDeclarationRules.isDeclaration("index.ts", "import { service } from './service';"));
        assertTrue(DependencyDeclarationRules.isDeclaration("index.js", "export { value } from './shared.js';"));
        assertTrue(DependencyDeclarationRules.isDeclaration("main.c", "#include <stdio.h>"));
        assertTrue(DependencyDeclarationRules.isDeclaration("a.cpp", "# include \"object.hpp\""));
        assertTrue(DependencyDeclarationRules.isDeclaration("Main.cs", "using System.Collections.Generic;"));
        assertTrue(DependencyDeclarationRules.isDeclaration("module.go", "import \"fmt\""));
        assertTrue(DependencyDeclarationRules.startsGoImportBlock("module.go", "import ("));
        assertTrue(DependencyDeclarationRules.isDeclaration("app.rb", "require_relative 'domain'"));
        assertTrue(DependencyDeclarationRules.isDeclaration("init.sh", "source ./helpers.sh"));
        assertTrue(DependencyDeclarationRules.isDeclaration("tools.ps1", "Import-Module Pester"));
        assertTrue(DependencyDeclarationRules.isDeclaration("test.asm", "%include \"macros.inc\""));
        assertFalse(DependencyDeclarationRules.isDeclaration("Main.java", "// import java.util.List;"));
        assertFalse(DependencyDeclarationRules.isDeclaration("Main.cs", "using (var file = Open()) {"));
        assertFalse(DependencyDeclarationRules.isDeclaration("hello.go", "fmt.Println(\"ok\")"));
        assertFalse(DependencyDeclarationRules.supports("not-practice.txt"));
    }
}
