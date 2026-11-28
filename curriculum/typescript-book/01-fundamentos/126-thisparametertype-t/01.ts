// Tema:
// ThisParameterType extrae el tipo del parámetro this explícito de una función.

interface Person {
    name: string;
    greet(this: Person): void;
}
type PersonThisType = ThisParameterType<Person['greet']>; // Person
