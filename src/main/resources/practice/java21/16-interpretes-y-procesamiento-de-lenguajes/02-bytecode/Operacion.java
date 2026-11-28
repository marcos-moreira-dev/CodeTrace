// Tema:
// javap desensambla bytecode de una clase compilada. Las instrucciones de la JVM no son instrucciones físicas del procesador.
// Modalidad: ejecución real con el JDK.
// Compilar: javac --release 21 -encoding UTF-8 Operacion.java
// Ejecutar: java Operacion
// Inspeccionar: javap -c -p Operacion (buscar iload, iadd e ireturn en sumar).

public class Operacion {
    static int sumar(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(sumar(3, 5));
    }
}

// Salida esperada:
// 8
