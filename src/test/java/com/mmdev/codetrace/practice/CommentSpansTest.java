package com.mmdev.codetrace.practice;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;

class CommentSpansTest {
    private static String left(String name, String source) {
        StringBuilder result = new StringBuilder(source);
        List<CommentSpans.Span> spans = CommentSpans.find(name, source);
        for (int i = spans.size() - 1; i >= 0; i--)
            result.delete(spans.get(i).start(), spans.get(i).end());
        return result.toString();
    }

    @Test void cFamilyAndStringLiterals() {
        assertEquals("String url=\"https://site/a\";\nint x=3;",
                left("Test.java", "String url=\"https://site/a\";\n// explanation\nint x=3;"));
        assertEquals("int x=1;  int y=2;",
                left("Test.cpp", "int x=1; /* explanation */ int y=2;"));
        assertEquals("val raw = \"\"\"/* not comment */\"\"\";\nval ok=1;",
                left("Test.kt", "val raw = \"\"\"/* not comment */\"\"\";\n/* outer /* nested */ text */\nval ok=1;"));
        assertEquals("var r=/[\\/]/;\nconst x=1;", left("a.ts",
                "var r=/[\\/]/;\n// note\nconst x=1;"));
        assertEquals("const x=1;", left("a.cs", "/* hi */\nconst x=1;"));
        assertEquals("x := 3", left("main.go", "// hi\nx := 3"));
        assertEquals("char *s=R\"(// no)\";\nx=1;",
                left("a.cpp", "char *s=R\"(// no)\";\n// note\nx=1;"));
    }

    @Test void scriptLanguagesIncludingPowerShellAndAsm() {
        assertEquals("#!/bin/bash\nprintf '%s' '# hi'\necho done",
                left("run.sh", "#!/bin/bash\nprintf '%s' '# hi'\n# note\necho done"));
        assertEquals("Write-Host '#not'; \n#Requires -Version 7\nWrite-Host hi",
                left("run.ps1", "Write-Host '#not'; # comment\n#Requires -Version 7\n<# block #>\nWrite-Host hi"));
        assertEquals("msg db 'a;b',0\nmov rax,1", left("hi.asm",
                "msg db 'a;b',0\n; note\nmov rax,1"));
        assertEquals("@echo off\necho hello", left("run.cmd",
                "@echo off\nREM hello\n:: comment\necho hello"));
        assertEquals("print('#')\nprint(2)", left("main.py",
                "print('#')\n# note\nprint(2)"));
        assertEquals("puts '#' \nputs 2", left("main.rb",
                "puts '#' # note\n=begin\ntext\n=end\nputs 2"));
    }

    @Test void configAndUnknownFilesStayConservative() {
        assertEquals("a: '#value'\nb: 2", left("x.yml", "a: '#value'\n# note\nb: 2"));
        assertEquals("body {color:red;}", left("x.css", "/* note */\nbody {color:red;}"));
        assertEquals("<root>ok</root>",left("x.xml","<!-- hello -->\n<root>ok</root>"));
        assertEquals("mov x,1\n",left("x.sql","-- comment\nmov x,1\n"));
        assertEquals("{\"url\":\"http://a\"}",left("x.json","{\"url\":\"http://a\"}"));
        assertEquals("hello # not code",left("x.txt","hello # not code"));
    }

    @Test void tripleStringsAndHereDocumentsCannotBecomeComments() {
        assertEquals("s = '''# string'''\nprint(1)",left("sample.py",
                "s = '''# string'''\n# comment\nprint(1)"));
        assertEquals("cat <<EOF\n# raw text\nEOF\necho ok",
                left("run.sh","cat <<EOF\n# raw text\nEOF\n# comment\necho ok"));
        assertEquals("$x = @'\n# literal\n'@\necho x",
                left("a.ps1","$x = @'\n# literal\n'@\n# note\necho x"));
    }
}
