// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.1.2; ejemplo 8.1.2-2
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
