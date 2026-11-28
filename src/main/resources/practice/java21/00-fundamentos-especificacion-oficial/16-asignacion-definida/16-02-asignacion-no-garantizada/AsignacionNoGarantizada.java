// Tema:
// Una asignación condicional no garantiza que la variable tenga valor en todas las rutas. El
// compilador rechaza la lectura si no puede probar la asignación definida.
// Compilar: javac --release 21 -encoding UTF-8 -d . AsignacionNoGarantizada.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class AsignacionNoGarantizada {
    public static void main(String[] args) {
        int valor;
        int n = 5;
        if (n > 2) valor = 9;
        System.out.println(valor);
    }
}

// Salida esperada:
// Error: variable valor might not have been initialized
