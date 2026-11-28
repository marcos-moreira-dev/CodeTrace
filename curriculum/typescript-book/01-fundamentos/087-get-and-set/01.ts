// Tema:
// Los accesores get y set permiten controlar la lectura y escritura de una propiedad mediante
// métodos.

class MyClass {
    private _myProperty: string;

    constructor(value: string) {
        this._myProperty = value;
    }
    get myProperty(): string {
        return this._myProperty;
    }
    set myProperty(value: string) {
        this._myProperty = value;
    }
}
