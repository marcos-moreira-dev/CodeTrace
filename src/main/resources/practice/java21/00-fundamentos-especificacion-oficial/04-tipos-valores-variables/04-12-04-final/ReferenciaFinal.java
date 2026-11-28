// Tema:
// final impide reasignar una variable una vez asignada. Si contiene una referencia, los campos
// del objeto referenciado aún pueden cambiar: la restricción no convierte al objeto en
// inmutable.
// Compilar: javac --release 21 -encoding UTF-8 -d . ReferenciaFinal.java Marcador.java
// Ejecutar: java ReferenciaFinal
// Comandos desde la carpeta de la unidad.

public class ReferenciaFinal {
    public static void main(String[] args) {
        final Marcador marcador = new Marcador();
        marcador.puntos = 5;
        System.out.println(marcador.puntos);
        // final impide cambiar la referencia, no el estado del objeto.
    }
}

// Salida esperada:
// 5
