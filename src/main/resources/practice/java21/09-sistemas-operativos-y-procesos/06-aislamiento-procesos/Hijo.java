// Tema:
// Cada JVM posee sus propias variables estáticas. Cambiar la variable del hijo no altera el estado del padre.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 Hijo.java Padre.java
// Ejecutar: java Padre

public class Hijo {
    public static void main(String[] args) throws Exception {
        int cantidad = 10;
        cantidad += 5;
        System.out.println("Hijo: " + cantidad);
    }
}

// Salida esperada:
// Hijo: 15
// Padre: 10
