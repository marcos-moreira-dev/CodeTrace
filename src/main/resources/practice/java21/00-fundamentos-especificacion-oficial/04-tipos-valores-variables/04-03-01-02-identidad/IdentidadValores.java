// Tema:
// Asignar un primitivo copia su valor; asignar una referencia copia la referencia y no el
// objeto. Dos variables que apuntan a la misma instancia observan las modificaciones de sus
// campos.
// Compilar: javac --release 21 -encoding UTF-8 -d . IdentidadValores.java Medicion.java
// Ejecutar: java IdentidadValores
// Comandos desde la carpeta de la unidad.

public class IdentidadValores {
    public static void main(String[] args) {
        int original = 7;
        int copia = original;
        copia = 9;
        System.out.println(original + " " + copia);

        // Ambas referencias apuntan al mismo objeto.
        Medicion primera = new Medicion();
        primera.valor = 12;
        Medicion segunda = primera;
        segunda.valor = 18;
        System.out.println(primera.valor + " " + segunda.valor);
    }
}

// Salida esperada:
// 7 9
// 18 18
