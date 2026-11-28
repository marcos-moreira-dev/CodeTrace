// Tema:
// Las operaciones sobre tipos existentes permiten construir contratos derivados sin repetir
// manualmente su estructura.

type ExtractParam<T> = T extends (param: infer P) => any ? P : never;
type MyFunction = (name: string) => number;
type ParamType = ExtractParam<MyFunction>; // string
