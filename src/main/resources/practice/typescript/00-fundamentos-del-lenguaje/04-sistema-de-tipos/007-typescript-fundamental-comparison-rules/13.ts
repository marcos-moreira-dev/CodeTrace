// Tema: typescript fundamental comparison rules.
// Los miembros privados introducen restricciones adicionales en la compatibilidad de clases.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 13.ts
// Diagnóstico esperado: TS2322; no ejecutar.

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

let x: X = new Y('y'); // Inválido
