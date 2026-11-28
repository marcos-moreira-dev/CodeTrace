// Tema:
// Los objetos literales reciben comprobaciones de propiedades adicionales; la compatibilidad
// estructural de una variable existente puede admitir más propiedades.

type X = {
    a: string;
};
const y = { a: 'a', b: 'b' };
const x: X = y; // Valid because structural typing
const w: X = { a: 'a', b: 'b' }; // Invalid because excess property checking
