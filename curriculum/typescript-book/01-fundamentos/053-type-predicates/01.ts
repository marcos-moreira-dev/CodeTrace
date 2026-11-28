// Tema:
// Un retorno de la forma valor is Tipo permite expresar una comprobación que reduce el tipo
// del argumento.

const isString = (value: unknown): value is string => typeof value === 'string';

const foo = (bar: unknown) => {
    if (isString(bar)) {
        console.log(bar.toUpperCase());
    } else {
        console.log('not a string');
    }
};
