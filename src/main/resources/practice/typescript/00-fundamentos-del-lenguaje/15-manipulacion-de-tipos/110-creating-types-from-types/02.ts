// Tema: creating types from types.
// Las operaciones sobre tipos existentes permiten construir contratos derivados sin repetir manualmente su estructura.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 02.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type Result = string | number;
const value1: Result = 'hello';
const value2: Result = 42;
