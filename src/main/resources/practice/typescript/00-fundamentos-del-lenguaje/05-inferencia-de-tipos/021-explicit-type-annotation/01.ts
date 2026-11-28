// Tema: explicit type annotation.
// Una anotación explícita fija el contrato estático que el valor debe satisfacer.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

const v = {
    x: 1, // Inferred type: number (widening)
};
v.x = 3; // Válido
