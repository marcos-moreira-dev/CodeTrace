// Tema:
// La interoperabilidad con tipos raw puede permitir una sobrescritura mediante conversión
// unchecked del retorno. Esa advertencia señala una garantía de tipos que el compilador no
// puede demostrar.
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
