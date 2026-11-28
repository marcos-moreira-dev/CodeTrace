// Tema: tuplas readonly.
// Readonly impide la escritura a través de la referencia. No implica congelación en ejecución.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 tuplas_readonly.ts
// Diagnóstico esperado: TS2540; no ejecutar.

const par: readonly [string,number] = ["Ana",20];
const nombre: string = par[0];
par[1] = 21;
