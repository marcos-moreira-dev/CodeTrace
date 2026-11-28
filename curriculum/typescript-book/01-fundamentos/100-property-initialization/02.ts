// Tema:
// El compilador puede comprobar que las propiedades requeridas de una clase estén
// inicializadas antes de usarlas.

class MyClass {
    property1: string;
    property2: number;

    constructor() {
        this.property1 = 'default value';
        this.property2 = 42;
    }
}
