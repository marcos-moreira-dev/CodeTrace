// Tema: covariance and contravariance in typescript.
// La covarianza de arreglos mutables permite una inserción insegura. El resultado muestra la limitación; readonly evita esta mutación.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 03.ts
// Ejecutar: node 03.js
// Salida esperada:
// false

class Animal { name = ""; }
class Dog extends Animal { breed = ""; }
const dogs: Dog[] = [new Dog()];
const animals: Animal[] = dogs;
animals.push(new Animal());
console.log(dogs[1] instanceof Dog);
