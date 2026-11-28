// Tema:
// La varianza describe cómo cambia la asignabilidad de un tipo compuesto cuando cambia su
// parámetro de tipo.

const dogs: Dog[] = [new Dog()];
const animals: Animal[] = dogs;

animals.push(new Animal()); // Allowed, but unsafe
// dogs now contains an Animal that is not a Dog.
