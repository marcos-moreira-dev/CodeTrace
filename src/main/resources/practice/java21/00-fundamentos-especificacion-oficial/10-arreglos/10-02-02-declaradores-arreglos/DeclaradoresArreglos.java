// Tema:
// Los corchetes pueden aparecer junto al tipo o al declarador. En una declaración múltiple,
// los corchetes de cada declarador determinan sus dimensiones adicionales.
// Compilar: javac --release 21 -encoding UTF-8 -d . DeclaradoresArreglos.java
// Ejecutar: java DeclaradoresArreglos
// Comandos desde la carpeta de la unidad.

public class DeclaradoresArreglos {
    public static void main(String[] args) {
        int a = 3, b[] = {4}, c[][] = {{5}};
        System.out.println(a);
        System.out.println(b[0]);
        System.out.println(c[0][0]);
    }
}

// Salida esperada:
// 3
// 4
// 5
