// Tema:
// Una interfaz que solo declara un método equivalente a un método público de Object no
// tiene un método abstracto propio. No puede ser una interfaz funcional.
// Compilar: javac --release 21 -encoding UTF-8 -d . SoloEquals.java
// ERROR INTENCIONAL de compilación. No ejecutar.

@FunctionalInterface
interface SoloEquals {
    boolean equals(Object otro);
}

// Salida esperada:
// Error: no abstract method found
