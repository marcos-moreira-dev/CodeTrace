// Tema:
// as comunica al compilador un tipo que el programador afirma conocer. No valida ni transforma
// el valor en ejecución.

type X = {
    a: string;
};
const x = {
    a: 'a',
    b: 'b',
} as X;
