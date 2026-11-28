// Tema: typescript fundamental comparison rules.
// Cambiar el nombre del parámetro genérico no cambia la firma.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 17.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type X = <T>(x: T) => T;
type Y = <K>(y: K) => K;
let x: X = x => x;
let y: Y = y => y;
x = y; // Válido
