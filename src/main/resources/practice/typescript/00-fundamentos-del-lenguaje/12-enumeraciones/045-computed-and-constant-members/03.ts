// Tema: computed and constant members.
// Un miembro de enum puede ser constante o calculado, lo que afecta las reglas de inicialización de sus miembros.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 03.ts
// Ejecutar: node 03.js
// Salida esperada:
// 42

const identity = (value: number) => value;

enum E {
    A = 2 * 5, // Numeric literal
    B = 'bar', // String literal
    C = identity(42), // Opaque computed
}

console.log(E.C); //42
