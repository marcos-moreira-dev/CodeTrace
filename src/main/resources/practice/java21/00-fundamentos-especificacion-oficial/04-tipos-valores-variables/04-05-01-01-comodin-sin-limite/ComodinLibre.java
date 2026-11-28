// Tema:
// List<?> representa una lista cuyo tipo de elemento se desconoce. Es seguro leer elementos
// como Object, pero no insertar un valor de un tipo arbitrario.
// Compilar: javac --release 21 -encoding UTF-8 -d . ComodinLibre.java
// Ejecutar: java ComodinLibre
// Comandos desde la carpeta de la unidad.

import java.util.List;
public class ComodinLibre {
    static void mostrar(List<?> lista) {
        for (Object objeto : lista) System.out.println(objeto);
    }
    public static void main(String[] args) {
        mostrar(List.of(3, 4));
        mostrar(List.of("sol", "mar"));
    }
}

// Salida esperada:
// 3
// 4
// sol
// mar
