// Tema:
// interface y type se solapan para objetos, pero difieren en fusión, uniones y otros usos del
// sistema de tipos.

interface A {
    x: string;
    y: number;
}

type B = A & {
    j: string;
};

const c: B = {
    x: 'x',
    y: 123,
    j: 'j',
};
