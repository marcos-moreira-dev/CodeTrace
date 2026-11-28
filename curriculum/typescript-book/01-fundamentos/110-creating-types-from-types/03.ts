// Tema:
// Las operaciones sobre tipos existentes permiten construir contratos derivados sin repetir
// manualmente su estructura.

type Mutable<T> = {
    readonly [P in keyof T]: T[P];
};
type Person = {
    name: string;
    age: number;
};
type ImmutablePerson = Mutable<Person>; // Properties become read-only
