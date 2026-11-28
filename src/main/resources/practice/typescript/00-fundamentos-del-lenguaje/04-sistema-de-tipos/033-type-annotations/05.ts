// Tema: type annotations.
// Las anotaciones expresan el tipo de variables, parámetros y retornos para detectar usos incompatibles.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 05.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

const sum = (a = 10, b: number): number => a + b;
