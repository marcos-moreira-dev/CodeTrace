// Tema: constructor.
// El constructor recibe los argumentos de creación e inicializa el estado de una instancia.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// Hello, my name is Simon and I'm 17 years old.

class Person {
    public name: string;
    public age: number;

    constructor(name: string, age: number) {
        this.name = name;
        this.age = age;
    }

    sayHello() {
        console.log(
            `Hello, my name is ${this.name} and I'm ${this.age} years old.`
        );
    }
}

const john = new Person('Simon', 17);
john.sayHello();
