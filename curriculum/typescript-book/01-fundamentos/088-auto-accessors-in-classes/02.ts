// Tema:
// accessor declara automáticamente el almacenamiento y los accesores de una propiedad de
// clase.

class Animal {
    #__name: string;

    get name() {
        return this.#__name;
    }
    set name(value: string) {
        this.#__name = value;
    }

    constructor(name: string) {
        this.name = name;
    }
}
