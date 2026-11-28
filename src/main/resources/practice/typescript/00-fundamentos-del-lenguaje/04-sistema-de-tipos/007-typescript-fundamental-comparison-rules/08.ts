// Tema: typescript fundamental comparison rules.
// Se conserva compatibilidad de firmas cuando el argumento adicional es opcional.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 08.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type X = (a: number) => undefined;
type Y = (a: number, b?: number) => undefined;
let x: X = a => undefined;
let y: Y = a => undefined;
y = x; // Válido
x = y; // Válido
