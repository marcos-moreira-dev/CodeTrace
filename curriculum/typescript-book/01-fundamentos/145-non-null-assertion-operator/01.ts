// Tema:
// El operador ! elimina null y undefined del tipo estático en esa expresión, pero no comprueba
// el valor real.

type Person = {
    name: string;
};

const printName = (person?: Person) => {
    console.log(`Name is ${person!.name}`);
};
