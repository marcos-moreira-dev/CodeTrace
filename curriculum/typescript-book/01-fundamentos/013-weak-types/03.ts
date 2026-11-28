// Tema:
// Un tipo formado por propiedades opcionales puede rechazar un objeto que no comparte ninguna
// de ellas.

type Options = {
    a?: string;
    b?: string;
};
const fn = (options: Options) => undefined;
fn({ c: 'c' } as Options); // Valid
