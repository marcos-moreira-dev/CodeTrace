// Tema: types as sets.
// Un tipo representa un conjunto de valores. La unión reúne alternativas; la intersección exige satisfacer ambos tipos.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 03.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

interface X {
    a: string;
}
interface Y extends X {
    b: string;
}
interface Z extends Y {
    c: string;
}
const z: Z = { a: 'a', b: 'b', c: 'c' };
interface X1 {
    a: string;
}
interface Y1 {
    a: string;
    b: string;
}
interface Z1 {
    a: string;
    b: string;
    c: string;
}
const z1: Z1 = { a: 'a', b: 'b', c: 'c' };

const r: Z1 = z; // Válido
