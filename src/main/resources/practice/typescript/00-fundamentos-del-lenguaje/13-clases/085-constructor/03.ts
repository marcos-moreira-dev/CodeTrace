// Tema: constructor.
// El constructor recibe los argumentos de creación e inicializa el estado de una instancia.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 03.ts
// Ejecutar: node 03.js
// Salida esperada:
// Name: Unknown, Age: 0
// Name: John, Age: 0
// Name: Jane, Age: 25

class Person {
    name: string;
    age: number;

    constructor();
    constructor(name: string);
    constructor(name: string, age: number);
    constructor(name?: string, age?: number) {
        this.name = name ?? 'Unknown';
        this.age = age ?? 0;
    }

    displayInfo() {
        console.log(`Name: ${this.name}, Age: ${this.age}`);
    }
}

const person1 = new Person();
person1.displayInfo(); // Name: Unknown, Age: 0

const person2 = new Person('John');
person2.displayInfo(); // Name: John, Age: 0

const person3 = new Person('Jane', 25);
person3.displayInfo(); // Name: Jane, Age: 25
