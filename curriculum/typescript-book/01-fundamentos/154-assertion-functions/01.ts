// Tema:
// Una función de aserción comunica al compilador una condición garantizada si retorna
// normalmente.

function isNumber(value: unknown): asserts value is number {
    if (typeof value !== 'number') {
        throw new Error('Not a number');
    }
}
