// Tema:
// La referencia a constructor difiere la creación hasta llamar a apply. int[]::new permite
// crear un arreglo cuando se proporciona su longitud.
// Compilar: javac --release 21 -encoding UTF-8 -d . Medida.java ReferenciasConstructor.java
// Ejecutar: java ReferenciasConstructor
// Comandos desde la carpeta de la unidad.

public class ReferenciasConstructor {
    public static void main(String[] args) {
        java.util.function.IntFunction<Medida> crear = Medida::new;
        java.util.function.IntFunction<int[]> arreglo = int[]::new;
        System.out.println(crear.apply(8).valor());
        System.out.println(arreglo.apply(3).length);
    }
}

// Salida esperada:
// 8
// 3
