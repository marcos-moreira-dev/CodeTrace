// Tema: computed and constant members.
// Un miembro de enum puede ser constante o calculado, lo que afecta las reglas de inicialización de sus miembros.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// 6

// Constant members
enum Color {
    Red = 1,
    Green = 5,
    Blue = Red + Green,
}
console.log(Color.Blue); // 6 generation at compilation time
