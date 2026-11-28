// Tema: private and protected constructors.
// Un constructor private o protected restringe los contextos que pueden crear instancias o extender una clase.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

class BaseClass {
    protected constructor() {}
}

class DerivedClass extends BaseClass {
    private value: number;

    constructor(value: number) {
        super();
        this.value = value;
    }
}

// Attempting to instantiate the base class directly will result in an error
// const baseObj = new BaseClass(); // Error: Constructor of class 'BaseClass' is protected.

// Create an instance of the derived class
const derivedObj = new DerivedClass(10);
