// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.3.2; ejemplo 8.3.2-2
// Compilar: javac --release 21 -encoding UTF-8 -d . ReferenciaPosterior.java
// Ejecutar: java ReferenciaPosterior
// Comandos desde la carpeta de la unidad.

public class ReferenciaPosterior {
    double dato = valor;
    static int valor = 9;
    public static void main(String[] args) {
        System.out.println(new ReferenciaPosterior().dato);
        valor = 4;
        System.out.println(new ReferenciaPosterior().dato);
    }
}

// Salida esperada:
// 9.0
// 4.0
