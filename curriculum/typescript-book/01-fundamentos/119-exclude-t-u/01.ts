// Tema:
// Exclude descarta de una unión los miembros asignables al tipo indicado.

type Union = 'a' | 'b' | 'c';
type MyType = Exclude<Union, 'a' | 'c'>; // b
