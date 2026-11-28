// Tema:
// Al extender una clase interna no estática, el constructor puede proporcionar explícitamente
// la instancia exterior mediante una invocación calificada a super.
// Compilar: javac --release 21 -encoding UTF-8 -d . Contenedor.java DerivadaInterna.java SuperCalificado.java
// Ejecutar: java SuperCalificado
// Comandos desde la carpeta de la unidad.

class Contenedor {
    class Interna { int valor = 5; }
}

// Salida esperada:
// 5
