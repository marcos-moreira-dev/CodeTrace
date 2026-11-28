// Tema: variadic tuple types.
// Las tuplas variádicas permiten expandir secuencias genéricas de elementos conservando información posicional.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type Student = [string, number];
const [studentName, age]: Student = ['Simone', 20];
