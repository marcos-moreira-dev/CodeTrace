// Tema:
// Partial hace opcionales todas las propiedades del tipo recibido.

type Person = {
    name: string;
    age: number;
};

type A = Partial<Person>; // { name?: string | undefined; age?: number | undefined; }
