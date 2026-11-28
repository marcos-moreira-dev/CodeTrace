// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §4.12.3; ejemplo 4.12.3-1
// Compilar: javac --release 21 -encoding UTF-8 -d . TiposVariables.java Sensor.java
// Ejecutar: java TiposVariables
// Comandos desde la carpeta de la unidad.

class Sensor {
    static int cantidad; // Variable de clase.
    int lectura;        // Variable de instancia.
    int[] muestras = new int[3]; // Componentes de arreglo.

    int cambiar(int nueva) { // Parámetro de método.
        int anterior = lectura; // Variable local.
        lectura = nueva;
        return anterior;
    }

    boolean igualA(Object otro) {
        return otro instanceof Sensor s && lectura == s.lectura;
        // s es una variable de patrón.
    }
}

// Salida esperada:
// 0
// true
// 0
