// Tema:
// Los argumentos de una invocación se evalúan de izquierda a derecha. Cada argumento conserva
// el valor calculado aunque uno posterior cambie una variable.
// Compilar: javac --release 21 -encoding UTF-8 -d . OrdenArgumentos.java
// Ejecutar: java OrdenArgumentos
// Comandos desde la carpeta de la unidad.

public class OrdenArgumentos {
    static void mostrar(String a, String b, String c) {
        System.out.println(a + " " + b + " " + c);
    }
    public static void main(String[] args) {
        String texto = "antes";
        mostrar(texto, texto, texto = "después");
    }
}

// Salida esperada:
// antes antes después
