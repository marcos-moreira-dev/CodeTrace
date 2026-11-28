// Tema:
// this identifica el receptor de una operación de instancia; su tipo y su valor dependen del
// contexto de llamada.

class Person {
    private name: string;
    constructor(name: string) {
        this.name = name;
    }
    public introduce(): void {
        console.log(`Hello, my name is ${this.name}.`);
    }
}

const person1 = new Person('Alice');
person1.introduce(); // Hello, my name is Alice.
