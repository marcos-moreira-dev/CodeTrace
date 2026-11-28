// Tema:
// Una función de aserción comunica al compilador una condición garantizada si retorna
// normalmente.

const isNumber = (value: unknown): value is number => typeof value === 'number';
