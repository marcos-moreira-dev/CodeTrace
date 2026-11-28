// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §4.12.4; ejemplo 4.12.4-1
// Compilar: javac --release 21 -encoding UTF-8 -d . ReferenciaFinal.java Marcador.java
// Ejecutar: java ReferenciaFinal
// Tema JLS:
// final impide asignar otra referencia a marcador, pero no impide modificar los campos del
// objeto. Cambiar puntos no modifica el valor de la variable marcador.
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
