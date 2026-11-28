// Tema:
// Una condición permite reducir las alternativas de un tipo unión en la rama donde la
// comprobación es verdadera.

let x: number | undefined = 10;

if (x !== undefined) {
    x += 100; // The type is number, which had been narrowed by the condition
}
