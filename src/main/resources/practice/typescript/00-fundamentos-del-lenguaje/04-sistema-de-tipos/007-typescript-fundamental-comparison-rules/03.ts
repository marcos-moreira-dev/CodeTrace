// Tema: typescript fundamental comparison rules.
// El retorno undefined no es intercambiable con number; ambas asignaciones se rechazan.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 03.ts
// Diagnóstico esperado: TS2322, TS2322; no ejecutar.

type X = (a: number) => undefined;
type Y = (a: number) => number;
let x: X = (a: number) => undefined;
let y: Y = (a: number) => 1;
y = x; // Inválido
x = y; // Inválido
