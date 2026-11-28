// Tema: type declaration.
// Una anotación explícita establece el tipo de una variable y permite comprobar que el valor asignado satisface ese contrato.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 02.ts
// Diagnóstico esperado: TS2353; no ejecutar.

type X = {
    a: string;
};

const x: X = {
    a: 'a',
    b: 'b', // Error: Object literal may only specify known properties
};
