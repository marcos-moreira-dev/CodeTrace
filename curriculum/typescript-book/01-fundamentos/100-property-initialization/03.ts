// Tema:
// El compilador puede comprobar que las propiedades requeridas de una clase estén
// inicializadas antes de usarlas.

class MyClass {
    constructor(
        private property1: string = 'default value',
        public property2: number = 42
    ) {
        // There is no need to assign the values to the properties explicitly.
    }
    log() {
        console.log(this.property2);
    }
}
const x = new MyClass();
x.log();
