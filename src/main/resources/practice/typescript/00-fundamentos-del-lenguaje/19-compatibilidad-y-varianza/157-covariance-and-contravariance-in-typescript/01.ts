// Tema: covariance and contravariance in typescript.
// La varianza describe cómo cambia la asignabilidad de un tipo compuesto cuando cambia su parámetro de tipo.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Diagnóstico esperado: TS2322; no ejecutar.

class Animal {
    name = '';
}

class Dog extends Animal {
    breed = '';
}

type Producer<T> = () => T;

declare let produceAnimal: Producer<Animal>;
declare let produceDog: Producer<Dog>;

produceAnimal = produceDog; // Válido
produceDog = produceAnimal; // Error
