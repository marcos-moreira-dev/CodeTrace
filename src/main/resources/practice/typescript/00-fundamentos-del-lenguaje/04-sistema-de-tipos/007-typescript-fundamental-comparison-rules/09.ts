// Tema: typescript fundamental comparison rules.
// Los parámetros rest permiten recibir argumentos adicionales.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 09.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type X = (a: number, ...rest: number[]) => undefined;
let x: X = a => undefined; //valid
