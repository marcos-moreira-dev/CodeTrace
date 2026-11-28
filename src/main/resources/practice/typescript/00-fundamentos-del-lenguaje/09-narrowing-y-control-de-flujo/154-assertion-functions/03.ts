// Tema: assertion functions.
// Una función de aserción comunica al compilador una condición garantizada si retorna normalmente.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 03.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

const isNumber = (value: unknown): value is number => typeof value === 'number';
