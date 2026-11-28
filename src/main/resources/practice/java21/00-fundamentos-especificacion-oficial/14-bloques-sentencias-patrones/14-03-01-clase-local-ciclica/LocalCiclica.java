// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §14.3; ejemplo 14.3-1
// Compilar: javac --release 21 -encoding UTF-8 -d . LocalCiclica.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class LocalCiclica {
    class Nodo {}
    void probar() {
        new Nodo(); // Se refiere a la clase miembro.
        class Nodo extends Nodo {} // El nombre local se refiere a sí mismo.
    }
}

// Salida esperada:
// Error: cyclic inheritance
