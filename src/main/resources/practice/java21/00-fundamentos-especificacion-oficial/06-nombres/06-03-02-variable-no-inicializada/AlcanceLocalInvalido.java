// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.3; ejemplo 6.3-2
// Compilar: javac --release 21 -encoding UTF-8 -d . AlcanceLocalInvalido.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class AlcanceLocalInvalido {
    public static void main(String[] args) {
        int valor = valor;
        System.out.println(valor);
    }
}

// Salida esperada:
// Error: variable valor might not have been initialized
