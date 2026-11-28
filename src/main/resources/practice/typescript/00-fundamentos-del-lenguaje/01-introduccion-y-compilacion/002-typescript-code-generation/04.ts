// Tema: Unión discriminada
// La propiedad kind existe durante la ejecución y permite al compilador distinguir las dos formas de Animal.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 04.ts
// Ejecutar: node 04.js
// Salida esperada:
// bark

interface Dog { kind: 'dog'; bark: () => void; }
interface Cat { kind: 'cat'; meow: () => void; }
type Animal = Dog | Cat;
function makeNoise(animal: Animal): void {
    if (animal.kind === 'dog') animal.bark();
    else animal.meow();
}
const dog: Dog = { kind: 'dog', bark: () => console.log('bark') };
makeNoise(dog);
