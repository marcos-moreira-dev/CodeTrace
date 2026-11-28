// Tema: typescript fundamental comparison rules.
// Un parámetro genérico no usado no diferencia la estructura; una interfaz vacía es demasiado permisiva.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 16.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

interface X<T> {}
const x: X<number> = 1;
const y: X<string> = 'a';
x === y; // Válido as the type argument is not used in the final structure
