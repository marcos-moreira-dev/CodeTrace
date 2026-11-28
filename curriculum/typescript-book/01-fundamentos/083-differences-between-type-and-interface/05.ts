// Tema:
// interface y type se solapan para objetos, pero difieren en fusión, uniones y otros usos del
// sistema de tipos.

interface A {
    x: 'x';
}
interface B {
    y: 'y';
}

type C = A | B; // Union of interfaces
