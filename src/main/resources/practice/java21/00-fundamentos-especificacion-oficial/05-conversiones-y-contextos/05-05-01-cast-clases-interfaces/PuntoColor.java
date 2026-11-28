// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §5.5; ejemplo 5.5-1
// Compilar: javac --release 21 -encoding UTF-8 -d . PuntoPlano.java Coloreable.java PuntoColor.java CastReferencias.java
// Ejecutar: java CastReferencias
// Comandos desde la carpeta de la unidad.

class PuntoColor extends PuntoPlano implements Coloreable {
    int tono;
    public void color(int n) { tono = n; }
}

// Salida esperada:
// 5
// cast inválido
