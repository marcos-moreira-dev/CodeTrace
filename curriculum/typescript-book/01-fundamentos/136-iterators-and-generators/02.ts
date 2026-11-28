// Tema:
// Un iterador entrega valores sucesivos; una función generadora usa yield para suspender y
// reanudar su ejecución.

function* numberGenerator(start: number, end: number): Generator<number> {
    for (let i = start; i <= end; i++) {
        yield i;
    }
}

const generator = numberGenerator(1, 5);

for (const num of generator) {
    console.log(num);
}
