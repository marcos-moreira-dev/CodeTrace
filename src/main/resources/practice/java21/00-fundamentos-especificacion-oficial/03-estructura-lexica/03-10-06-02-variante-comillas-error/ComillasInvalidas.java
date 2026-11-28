// Tema:
// Cuatro comillas consecutivas sin escape al final del contenido se interpretan como un
// delimitador de cierre de tres comillas y una comilla sobrante. El literal no es válido.
// Compilar: javac --release 21 -encoding UTF-8 -d . ComillasInvalidas.java
// ERROR INTENCIONAL de compilación. No ejecutar.

class ComillasInvalidas {
    String texto = """
            dijo "hola"""";
}

// Salida esperada:
// Error: unclosed string literal
