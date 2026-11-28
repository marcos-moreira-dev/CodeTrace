// Tema:
// ?. detiene una cadena de acceso o invocación cuando su receptor es null o undefined.

type Person = {
    name: string;
    age?: number;
    address?: {
        street?: string;
        city?: string;
    };
};

const person: Person = {
    name: 'John',
};

console.log(person.address?.city); // undefined
