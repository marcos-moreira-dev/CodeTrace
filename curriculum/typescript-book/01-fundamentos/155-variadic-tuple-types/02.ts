// Tema:
// Las tuplas variádicas permiten expandir secuencias genéricas de elementos conservando
// información posicional.

type Bar<T extends unknown[]> = [boolean, ...T, number];

type A = Bar<[boolean]>; // [boolean, boolean, number]
type B = Bar<['a', 'b']>; // [boolean, 'a', 'b', number]
type C = Bar<[]>; // [boolean, number]
