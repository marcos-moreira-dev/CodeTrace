// Tema:
// La varianza describe cómo cambia la asignabilidad de un tipo compuesto cuando cambia su
// parámetro de tipo.

type Consumer<T> = (value: T) => void;

declare let consumeAnimal: Consumer<Animal>;
declare let consumeDog: Consumer<Dog>;

consumeDog = consumeAnimal; // Valid
consumeAnimal = consumeDog; // Error with strictFunctionTypes
