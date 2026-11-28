// Tema:
// Una anotación explícita establece el tipo de una variable y permite comprobar que el valor
// asignado satisface ese contrato.

type X = {
    a: string;
};

const x: X = {
    a: 'a',
    b: 'b', // Error: Object literal may only specify known properties
};
