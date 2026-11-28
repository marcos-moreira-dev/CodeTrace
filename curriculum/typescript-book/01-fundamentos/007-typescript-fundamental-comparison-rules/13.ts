// Tema:
// La asignabilidad depende de las propiedades, los parámetros y retornos de las funciones, y
// las restricciones de miembros privados o protegidos. Los ejemplos muestran asignaciones
// aceptadas y rechazadas.

class X {
    public a: string;
    constructor(value: string) {
        this.a = value;
    }
}

class Y {
    private a: string;
    constructor(value: string) {
        this.a = value;
    }
}

let x: X = new Y('y'); // Invalid
