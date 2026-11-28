// Tema:
// El tipo parametrizado List<String> se comprueba durante la compilación; el argumento de
// tipo se borra en la representación de clase en ejecución. Dos ArrayList con argumentos de
// tipo distintos comparten la misma clase.
// Compilar: javac --release 21 -encoding UTF-8 -d . BorradoTipos.java
// Ejecutar: java BorradoTipos
// Comandos desde la carpeta de la unidad.

public class BorradoTipos {
    public static void main(String[] args) {
        java.util.ArrayList<String> palabras = new java.util.ArrayList<>();
        java.util.ArrayList<Integer> numeros = new java.util.ArrayList<>();
        System.out.println(palabras.getClass() == numeros.getClass());
    }
}

// Salida esperada:
// true
