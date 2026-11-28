// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §4.5.1; ejemplo 4.5.1-1
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
