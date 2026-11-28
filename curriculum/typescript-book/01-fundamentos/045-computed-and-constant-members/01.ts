// Tema:
// Un miembro de enum puede ser constante o calculado, lo que afecta las reglas de
// inicialización de sus miembros.

// Constant members
enum Color {
    Red = 1,
    Green = 5,
    Blue = Red + Green,
}
console.log(Color.Blue); // 6 generation at compilation time
