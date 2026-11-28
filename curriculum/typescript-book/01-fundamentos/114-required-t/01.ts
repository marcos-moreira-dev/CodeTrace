// Tema:
// Required elimina la opcionalidad de las propiedades del tipo recibido.

type Person = {
    name?: string;
    age?: number;
};

type A = Required<Person>; // { name: string; age: number; }
