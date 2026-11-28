// Tema:
// Un campo static pertenece a la clase, un campo de instancia a cada objeto, y un componente
// de arreglo a una posición. Los parámetros reciben argumentos y las variables locales o de
// patrón tienen un alcance determinado.
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
