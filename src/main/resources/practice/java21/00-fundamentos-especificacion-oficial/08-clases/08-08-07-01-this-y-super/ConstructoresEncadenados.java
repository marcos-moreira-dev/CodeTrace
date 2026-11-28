// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.8.7; ejemplo 8.8.7-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Elemento.java ElementoMarcado.java ConstructoresEncadenados.java
// Ejecutar: java ConstructoresEncadenados
// Comandos desde la carpeta de la unidad.

public class ConstructoresEncadenados {
    public static void main(String[] args) {
        ElementoMarcado a = new ElementoMarcado(5);
        System.out.println(a.numero + " " + a.marca);
    }
}

// Salida esperada:
// 5 normal
