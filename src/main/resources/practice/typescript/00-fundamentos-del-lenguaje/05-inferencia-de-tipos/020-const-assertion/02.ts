// Tema: const assertion.
// as const aplicado al objeto produce propiedades readonly y conserva sus literales.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 02.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

const v = {
    x: 1,
    y: 2,
} as const;
