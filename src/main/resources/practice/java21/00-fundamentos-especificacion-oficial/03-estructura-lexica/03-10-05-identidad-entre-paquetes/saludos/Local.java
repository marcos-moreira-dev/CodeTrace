// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §3.10.5; ejemplo 3.10.5-1
// Compilar: javac --release 21 -encoding UTF-8 -d . saludos/IdentidadCadenas.java saludos/Local.java externo/Externo.java
// Ejecutar: java saludos.IdentidadCadenas
// Comandos desde la carpeta de la unidad.

package saludos;
class Local {
    static String PALABRA = "luna";
}

// Salida esperada:
// true
// true
// true
// true
// false
// true
