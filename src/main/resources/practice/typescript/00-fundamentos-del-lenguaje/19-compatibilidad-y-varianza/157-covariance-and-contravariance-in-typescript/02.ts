// Tema: covariance and contravariance in typescript.
// Con strictFunctionTypes una función que acepta cualquier Animal puede consumir Dog, pero no a la inversa.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 02.ts
// Diagnóstico esperado: TS2322; no ejecutar.

class Animal { name = ""; }
class Dog extends Animal { breed = ""; }
type Consumer<T> = (value: T) => void;
declare let consumeAnimal: Consumer<Animal>;
declare let consumeDog: Consumer<Dog>;
consumeDog = consumeAnimal;
consumeAnimal = consumeDog;
