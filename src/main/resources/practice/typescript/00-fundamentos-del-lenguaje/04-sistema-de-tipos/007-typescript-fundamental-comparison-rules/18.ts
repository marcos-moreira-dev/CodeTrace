// Tema: typescript fundamental comparison rules.
// unknown requiere comprobación; never no acepta valores ordinarios y void no admite number.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 18.ts
// Diagnóstico esperado: TS2322, TS2322, TS2322; no ejecutar.

let a: number = 1;
let b: number = 2;
a = b; // Válido, everything is assignable to itself

let c: any;
c = 1; // Válido, all types are assignable to any

let d: unknown;
d = 1; // Válido, all types are assignable to unknown

let e: unknown;
let e1: unknown = e; // Válido, unknown is only assignable to itself and any
let e2: any = e; // Válido
let e3: number = e; // Inválido

let f: never;
f = 1; // Inválido, nothing is assignable to never

let g: void;
let g1: any;
g = 1; // Inválido, void is not assignable to or from anything except any
g = g1; // Válido
