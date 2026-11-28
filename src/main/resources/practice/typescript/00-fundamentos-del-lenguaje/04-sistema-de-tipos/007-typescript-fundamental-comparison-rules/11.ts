// Tema: typescript fundamental comparison rules.
// Un objeto de una subclase puede suministrarse a una función que acepta la clase base. No confundir esto con bivarianza.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 11.ts
// Ejecutar: node 11.js
// Salida esperada:
// x
// Y
// z

// Supertipo
class X {
    a: string;
    constructor(value: string) {
        this.a = value;
    }
}
// Subtipo
class Y extends X {}
// Subtipo
class Z extends X {}

type GetA = (x: X) => string;
const getA: GetA = x => x.a;

// Una subclase satisface el contrato de la clase base
console.log(getA(new X('x'))); // Válido
console.log(getA(new Y('Y'))); // Válido
console.log(getA(new Z('z'))); // Válido
