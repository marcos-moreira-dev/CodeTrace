// Tema:
// javac -g conserva información de depuración; jdb permite detenerse en métodos, inspeccionar variables, avanzar y examinar la pila. La ejecución normal también es verificable.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac -g --release 21 ProgramaDepurable.java
// Ejecutar: java ProgramaDepurable
// Depurar: jdb ProgramaDepurable; dentro de jdb: stop in ProgramaDepurable.total; run; locals; next; step; where; cont. Usar quit para salir.

public class ProgramaDepurable {
    static int total(int precio, int unidades) {
        int subtotal = precio * unidades;
        return subtotal;
    }

    public static void main(String[] args) throws Exception {
        int precio = 200;
        int unidades = 3;
        System.out.println(total(precio, unidades));
    }
}

// Salida esperada:
// 600
