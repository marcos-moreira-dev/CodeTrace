// Tema: Tipos borrados e instanceof
// Una interfaz solo describe una forma durante la comprobación. instanceof requiere un constructor real, no una interfaz.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 03.ts
// Diagnóstico esperado:
// El compilador debe rechazar el ejemplo (TS2693). No ejecutar.

interface Animal { name: string; }
interface Dog extends Animal { bark(): void; }
interface Cat extends Animal { meow(): void; }
function makeNoise(animal: Animal): void {
    if (animal instanceof Dog) {
        // Dog no existe como valor durante la ejecución.
    }
}
