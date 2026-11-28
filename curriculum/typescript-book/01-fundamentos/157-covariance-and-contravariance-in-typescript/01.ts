// Tema:
// La varianza describe cómo cambia la asignabilidad de un tipo compuesto cuando cambia su
// parámetro de tipo.

class Animal {
    name = '';
}

class Dog extends Animal {
    breed = '';
}

type Producer<T> = () => T;

declare let produceAnimal: Producer<Animal>;
declare let produceDog: Producer<Dog>;

produceAnimal = produceDog; // Valid
produceDog = produceAnimal; // Error
