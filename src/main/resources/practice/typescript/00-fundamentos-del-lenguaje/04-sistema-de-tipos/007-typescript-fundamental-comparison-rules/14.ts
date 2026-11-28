// Tema: typescript fundamental comparison rules.
// Una clase no necesita heredar de otra para tener estructura pública compatible.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 14.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

class X {
    public a: string;
    constructor(value: string) {
        this.a = value;
    }
}
class Y extends X {
    public a: string;
    constructor(value: string) {
        super(value);
        this.a = value;
    }
}
class Z {
    public a: string;
    constructor(value: string) {
        this.a = value;
    }
}
let x: X = new X('x');
let y: Y = new Y('y');
let z: Z = new Z('z');
x === y; // Válido
x === z; // Válido even if z is from a different inheritance hierarchy
