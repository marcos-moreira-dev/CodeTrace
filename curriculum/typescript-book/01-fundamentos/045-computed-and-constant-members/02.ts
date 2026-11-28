// Tema:
// Un miembro de enum puede ser constante o calculado, lo que afecta las reglas de
// inicialización de sus miembros.

// Computed members
enum Color {
    Red = 1,
    Green = Math.pow(2, 2),
    Blue = Math.floor(Math.random() * 3) + 1,
}
console.log(Color.Blue); // random number generated at run time
