// Tema:
// Un arreglo contiene elementos de un tipo determinado; puede declararse con T[] o Array<T>.

const x: readonly string[] = ['a', 'b']; // Readonly modifier
const y: ReadonlyArray<string> = ['a', 'b'];
const j: ReadonlyArray<string | number> = ['a', 1, 'b', 2];
j.push('x'); // Invalid
