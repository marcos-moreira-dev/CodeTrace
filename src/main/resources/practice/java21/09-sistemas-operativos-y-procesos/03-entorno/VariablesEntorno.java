// Tema:
// El proceso recibe un entorno de su padre. Solo consultamos la presencia de una variable conocida, sin exponer su valor ni otras variables.
// Modalidad: ejecución real mediante API estándar de Java.
// Compilar: javac --release 21 -encoding UTF-8 VariablesEntorno.java
// Ejecutar: java VariablesEntorno

public class VariablesEntorno {
    public static void main(String[] args) throws Exception {
        String nombre = "CODETRACE_DEMO_OPCIONAL";
        System.out.println("Variable disponible: " + (System.getenv(nombre) != null));
    }
}

// Salida esperada:
// Variable disponible: false
// También puede observarse: Variable disponible: true
