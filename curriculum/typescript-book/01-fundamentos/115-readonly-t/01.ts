// Tema:
// Readonly marca como solo lectura las propiedades del tipo recibido.

type Person = {
    name: string;
    age: number;
};

type A = Readonly<Person>;

const a: A = { name: 'Simon', age: 17 };
a.name = 'John'; // Invalid
