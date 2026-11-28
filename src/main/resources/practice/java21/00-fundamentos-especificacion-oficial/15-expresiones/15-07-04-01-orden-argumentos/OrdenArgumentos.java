// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.7.4; ejemplo 15.7.4-1
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
