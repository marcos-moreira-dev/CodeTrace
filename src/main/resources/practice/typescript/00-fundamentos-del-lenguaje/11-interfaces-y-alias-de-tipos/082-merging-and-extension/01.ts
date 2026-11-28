// Tema: merging and extension.
// Las declaraciones de interfaz pueden fusionarse o extenderse; los alias de tipo no admiten fusión de declaraciones.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

interface X {
    a: string;
}

interface X {
    b: number;
}

const person: X = {
    a: 'a',
    b: 7,
};
