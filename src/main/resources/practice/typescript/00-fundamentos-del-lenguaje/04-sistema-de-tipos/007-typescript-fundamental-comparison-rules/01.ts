// Tema: typescript fundamental comparison rules.
// La asignación admite propiedades adicionales en una variable si cumple el contrato requerido.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type X = {
    a: string;
};
const y = { a: 'A', b: 'B' }; // Válido, as it has at least the same members as X
const r: X = y;
