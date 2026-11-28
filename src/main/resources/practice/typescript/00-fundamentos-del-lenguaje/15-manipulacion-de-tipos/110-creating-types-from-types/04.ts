// Tema: creating types from types.
// Las operaciones sobre tipos existentes permiten construir contratos derivados sin repetir manualmente su estructura.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 04.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type ExtractParam<T> = T extends (param: infer P) => any ? P : never;
type MyFunction = (name: string) => number;
type ParamType = ExtractParam<MyFunction>; // string
