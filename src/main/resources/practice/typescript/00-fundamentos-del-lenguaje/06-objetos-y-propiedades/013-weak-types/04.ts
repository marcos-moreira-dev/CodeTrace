// Tema: weak types.
// Un tipo formado por propiedades opcionales puede rechazar un objeto que no comparte ninguna de ellas.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 04.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

type Options = {
    [prop: string]: unknown;
    a?: string;
    b?: string;
};

const fn = (options: Options) => undefined;
fn({ c: 'c' }); // Válido
