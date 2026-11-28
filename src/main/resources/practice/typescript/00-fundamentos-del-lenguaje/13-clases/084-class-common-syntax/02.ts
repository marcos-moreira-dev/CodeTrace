// Tema: class common syntax.
// Instanciar una clase ejecuta su constructor y permite invocar métodos sobre el objeto.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 02.ts
// Ejecutar: node 02.js
// Salida esperada:
// Hello, my name is John Doe and I am 25 years old.

class Person { constructor(private name: string, private age: number) {} sayHi(): void { console.log(`Hello, my name is ${this.name} and I am ${this.age} years old.`); } }
const myObject = new Person('John Doe',25);
myObject.sayHi();
