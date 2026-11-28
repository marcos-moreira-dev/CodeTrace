// Tema:
// interface y type se solapan para objetos, pero difieren en fusión, uniones y otros usos del
// sistema de tipos.

interface A {
    x: string;
    y: number;
}
interface B extends A {
    z: string;
}
const car: B = {
    x: 'x',
    y: 123,
    z: 'z',
};
