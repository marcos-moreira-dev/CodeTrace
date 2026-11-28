// Tema: common syntax.
// interface y type permiten describir contratos de objetos; sus sintaxis y posibilidades no son completamente equivalentes.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 03.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

interface Person {
    name: string;
    age: number;
    greet(): void;
}
