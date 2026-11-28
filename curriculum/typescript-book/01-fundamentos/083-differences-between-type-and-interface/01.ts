// Tema:
// interface y type se solapan para objetos, pero difieren en fusión, uniones y otros usos del
// sistema de tipos.

interface A {
    x: string;
}
interface A {
    y: string;
}
const j: A = {
    x: 'xx',
    y: 'yy',
};
