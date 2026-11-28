// Tema:
// Class.forName con initialize=false localiza/carga la clase sin solicitar inicialización. La segunda llamada con true inicializa; el bloque static solo se ejecuta una vez por cargador.
// Modalidad: ejecución real con el JDK.
// Compilar: javac --release 21 -encoding UTF-8 ClaseEjemplo.java MainCargaClase.java
// Ejecutar: java MainCargaClase

public class MainCargaClase {
    public static void main(String[] args) throws Exception {
        ClassLoader cargador = MainCargaClase.class.getClassLoader();
        Class<?> clase = Class.forName("ClaseEjemplo", false, cargador);
        System.out.println("Cargada: " + clase.getSimpleName());
        Class.forName("ClaseEjemplo", true, cargador);
        Class.forName("ClaseEjemplo", true, cargador);
        System.out.println("Fin");
    }
}

// Salida esperada:
// Cargada: ClaseEjemplo
// Inicializada
// Fin
