// Tema: typescript fundamental comparison rules.
// Una función puede ignorar argumentos adicionales que su cliente suministra.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 06.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type X = (a: number) => undefined;
type Y = (a: number, b: number) => undefined;
let x: X = (a: number) => undefined;
let y: Y = (a: number) => undefined; // Missing b parameter
y = x; // Válido
