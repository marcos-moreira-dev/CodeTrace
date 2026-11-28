// Tema: weak types.
// Un tipo formado por propiedades opcionales puede rechazar un objeto que no comparte ninguna de ellas.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 02.ts
// Diagnóstico esperado: TS2353; no ejecutar.

type Options = {
    a?: string;
    b?: string;
};

const fn = (options: Options) => undefined;

fn({ c: 'c' }); // Inválido
