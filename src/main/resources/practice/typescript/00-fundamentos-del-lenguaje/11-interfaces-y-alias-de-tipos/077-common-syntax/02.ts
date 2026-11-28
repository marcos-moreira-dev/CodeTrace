// Tema: common syntax.
// Un alias puede describir el mismo contrato estructural sin utilizar interface.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 02.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type Operacion = { nombre: string; ejecutar(a: number, b: number): number };
const sumar: Operacion = { nombre: "suma", ejecutar(a,b) { return a+b; } };
