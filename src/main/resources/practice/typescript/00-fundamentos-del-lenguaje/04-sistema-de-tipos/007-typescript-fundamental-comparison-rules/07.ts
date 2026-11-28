// Tema: typescript fundamental comparison rules.
// Los parámetros opcionales no obligan a los clientes a proporcionar valores.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 07.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type X = (a: number, b?: number, c?: number) => undefined;
type Y = (a: number) => undefined;
let x: X = a => undefined;
let y: Y = a => undefined;
y = x; // Válido
x = y; //Valid
