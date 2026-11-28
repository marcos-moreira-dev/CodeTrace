// Tema: common syntax.
// interface y type permiten describir contratos de objetos; sus sintaxis y posibilidades no son completamente equivalentes.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 04.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type TypeName = {
    property1: string;
    method1(arg1: string, arg2: string): string;
};
