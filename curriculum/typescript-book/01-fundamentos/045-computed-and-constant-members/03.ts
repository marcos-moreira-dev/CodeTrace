// Tema:
// Un miembro de enum puede ser constante o calculado, lo que afecta las reglas de
// inicialización de sus miembros.

const identity = (value: number) => value;

enum E {
    A = 2 * 5, // Numeric literal
    B = 'bar', // String literal
    C = identity(42), // Opaque computed
}

console.log(E.C); //42
