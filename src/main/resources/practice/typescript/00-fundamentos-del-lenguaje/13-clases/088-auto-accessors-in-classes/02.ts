// Tema: auto accessors in classes.
// El campo privado se inicializa directamente en el constructor; el getter y setter controlan el acceso.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 02.ts
// Ejecutar: node 02.js
// Salida esperada:
// Sol

class Animal {
    #__name: string;
    get name(): string { return this.#__name; }
    set name(value: string) { this.#__name = value; }
    constructor(name: string) { this.#__name = name; }
}
const a = new Animal("Luna");
a.name = "Sol";
console.log(a.name);
