// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.8.3; ejemplo 8.4.8.3-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Generador.java GeneradorEntero.java RetornoCovariante.java
// Ejecutar: java RetornoCovariante
// Comandos desde la carpeta de la unidad.

public class RetornoCovariante {
    public static void main(String[] args) {
        Integer n = new GeneradorEntero().obtener();
        System.out.println(n);
    }
}

// Salida esperada:
// 7
