// Tema: property checking and excess property checking.
// Los objetos literales reciben comprobaciones de propiedades adicionales; la compatibilidad estructural de una variable existente puede admitir más propiedades.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Diagnóstico esperado: TS2353; no ejecutar.

type X = {
    a: string;
};
const y = { a: 'a', b: 'b' };
const x: X = y; // Válido because structural typing
const w: X = { a: 'a', b: 'b' }; // Inválido because excess property checking
