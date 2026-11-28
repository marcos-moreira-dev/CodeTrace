// Tema:
// new.target permite distinguir si se ha invocado una función como constructor y qué
// constructor inició la creación.

class Parent {
    constructor() {
        console.log(new.target); // Logs the constructor function used to create an instance
    }
}

class Child extends Parent {
    constructor() {
        super();
    }
}

const parentX = new Parent(); // [Function: Parent]
const child = new Child(); // [Function: Child]
