// Tema: array.
// Un arreglo contiene elementos de un tipo determinado; puede declararse con T[] o Array<T>.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 02.ts
// Diagnóstico esperado: TS2339; no ejecutar.

const x: readonly string[] = ['a', 'b']; // Readonly modifier
const y: ReadonlyArray<string> = ['a', 'b'];
const j: ReadonlyArray<string | number> = ['a', 1, 'b', 2];
j.push('x'); // Inválido
