// Tema: type declaration.
// Una anotación explícita establece el tipo de una variable y permite comprobar que el valor asignado satisface ese contrato.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type X = {
    a: string;
};

// Type declaration
const x: X = {
    a: 'a',
};
