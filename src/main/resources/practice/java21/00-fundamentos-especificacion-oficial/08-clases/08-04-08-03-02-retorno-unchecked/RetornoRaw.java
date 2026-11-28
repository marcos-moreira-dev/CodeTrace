// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.8.3; ejemplo 8.4.8.3-2
// Compilar: javac --release 21 -encoding UTF-8 -d . Ordenador.java OrdenadorAntiguo.java RetornoRaw.java
// Ejecutar: java RetornoRaw
// Comandos desde la carpeta de la unidad.

class RetornoRaw {
    public static void main(String[] args) {
        Ordenador o = new OrdenadorAntiguo();
        System.out.println(o.lista(java.util.List.of("a", "b")));
    }
}

// Salida esperada:
// [a, b]
