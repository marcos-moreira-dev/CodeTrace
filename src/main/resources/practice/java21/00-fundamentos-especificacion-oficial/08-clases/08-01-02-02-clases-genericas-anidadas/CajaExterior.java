// Tema:
// Una clase interna no estática puede utilizar el parámetro de tipo de su instancia exterior y
// declarar parámetros propios. El tipo completo conserva ambos argumentos.
// Compilar: javac --release 21 -encoding UTF-8 -d . CajaExterior.java GenericasAnidadas.java
// Ejecutar: java GenericasAnidadas
// Comandos desde la carpeta de la unidad.

class CajaExterior<T> {
    T dato;
    class Interior<U> {
        U otro;
        Interior(U otro) { this.otro = otro; }
        void mostrar() { System.out.println(dato + " " + otro); }
    }
}

// Salida esperada:
// texto 8
