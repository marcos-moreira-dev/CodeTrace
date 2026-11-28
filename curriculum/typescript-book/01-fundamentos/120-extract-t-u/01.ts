// Tema:
// Extract conserva de una unión los miembros asignables al tipo indicado.

type Union = 'a' | 'b' | 'c';
type MyType = Extract<Union, 'a' | 'c'>; // a | c
