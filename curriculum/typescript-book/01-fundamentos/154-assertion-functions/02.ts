// Tema:
// Una función de aserción comunica al compilador una condición garantizada si retorna
// normalmente.

type AssertIsNumber = (value: unknown) => asserts value is number;
const isNumber: AssertIsNumber = value => {
    if (typeof value !== 'number') {
        throw new Error('Not a number');
    }
};
