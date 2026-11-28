// Tema:
// Un cast de referencia comprueba que el objeto real satisface la clase o interfaz destino.
// Las referencias convertidas siguen apuntando al mismo objeto; una conversión incompatible
// lanza ClassCastException.
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
