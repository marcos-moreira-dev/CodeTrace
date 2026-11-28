// Tema: common syntax.
// Una interfaz declara propiedades y métodos. El objeto debe satisfacer ambas firmas.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

interface Operacion { nombre: string; ejecutar(a: number, b: number): number; }
const sumar: Operacion = { nombre: "suma", ejecutar(a,b) { return a+b; } };
