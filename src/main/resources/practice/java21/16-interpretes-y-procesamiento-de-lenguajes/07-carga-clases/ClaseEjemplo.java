// Tema:
// Class.forName con initialize=false localiza/carga la clase sin solicitar inicialización. La segunda llamada con true inicializa; el bloque static solo se ejecuta una vez por cargador.
// Modalidad: ejecución real con el JDK.
// Compilar: javac --release 21 -encoding UTF-8 ClaseEjemplo.java MainCargaClase.java
// Ejecutar: java MainCargaClase

public class ClaseEjemplo {
    static { System.out.println("Inicializada"); }
}

// Salida esperada:
// Cargada: ClaseEjemplo
// Inicializada
// Fin
