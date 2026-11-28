// Tema:
// Una salida anticipada permite descartar las alternativas que no pueden alcanzar el código
// siguiente.

let x: number | undefined = 10;

if (x === undefined) {
    throw 'error';
}
x += 100;
