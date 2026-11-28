// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §4.12.3; ejemplo 4.12.3-1
// Compilar: javac --release 21 -encoding UTF-8 -d . TiposVariables.java Sensor.java
// Ejecutar: java TiposVariables
// Comandos desde la carpeta de la unidad.

public class TiposVariables {
    public static void main(String[] args) {
        Sensor s = new Sensor();
        System.out.println(s.cambiar(8));
        System.out.println(s.igualA(s));
        System.out.println(s.muestras[0]);
    }
}

// Salida esperada:
// 0
// true
// 0
