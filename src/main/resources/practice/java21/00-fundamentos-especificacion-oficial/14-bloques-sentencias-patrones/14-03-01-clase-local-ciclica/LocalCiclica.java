// Tema:
// El alcance de una declaración de clase local incluye su propio cuerpo y encabezado
// pertinente. Si el nombre de la superclase se resuelve a ella misma, la herencia es circular.
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
