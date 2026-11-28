// Tema: const assertion.
// as const en una propiedad restringe su valor literal, pero no vuelve readonly al objeto entero.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

const v = {
    x: 3 as const,
};
v.x = 3;
