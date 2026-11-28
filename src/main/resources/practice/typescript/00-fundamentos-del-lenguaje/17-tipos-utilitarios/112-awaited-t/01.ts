// Tema: awaited t.
// Awaited obtiene el tipo resultante de resolver una promesa, incluidas promesas anidadas.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type A = Awaited<Promise<string>>; // string
