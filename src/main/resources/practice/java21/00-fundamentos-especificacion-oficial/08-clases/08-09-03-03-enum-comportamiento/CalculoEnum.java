// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.9.3; ejemplo 8.9.3-3
// Compilar: javac --release 21 -encoding UTF-8 -d . CalculoEnum.java
// Ejecutar: java CalculoEnum
// Comandos desde la carpeta de la unidad.

enum CalculoEnum {
    SUMA { int operar(int a, int b) { return a + b; } },
    PRODUCTO { int operar(int a, int b) { return a * b; } };
    abstract int operar(int a, int b);
    public static void main(String[] args) {
        for (CalculoEnum c : values()) System.out.println(c.operar(3, 5));
    }
}

// Salida esperada:
// 8
// 15
