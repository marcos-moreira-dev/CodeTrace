// Tema:
// Las operaciones sobre tipos existentes permiten construir contratos derivados sin repetir
// manualmente su estructura.

type A = { foo: number };
type B = { bar: string };
type C = A & B; // Intersection of A and B
const obj: C = { foo: 42, bar: 'hello' };
