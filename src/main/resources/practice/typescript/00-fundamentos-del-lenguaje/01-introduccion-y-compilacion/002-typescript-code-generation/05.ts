// Tema: Clases como tipos y valores
// Las clases producen constructores reales. instanceof comprueba la cadena de prototipos y estrecha el tipo de la unión.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 05.ts
// Ejecutar: node 05.js
// Salida esperada:
// bark

class Animal { constructor(public name: string) {} }
class Dog extends Animal {
    constructor(name: string, public bark: () => void) { super(name); }
}
class Cat extends Animal {
    constructor(name: string, public meow: () => void) { super(name); }
}
type Mammal = Dog | Cat;
function makeNoise(mammal: Mammal): void {
    if (mammal instanceof Dog) mammal.bark();
    else mammal.meow();
}
makeNoise(new Dog('Fido', () => console.log('bark')));
