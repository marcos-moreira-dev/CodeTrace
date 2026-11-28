// Tema:
// extends restringe los argumentos de tipo admitidos y permite utilizar los miembros
// garantizados por la restricción.

const printLen = <T extends { length: number }>(value: T): void => {
    console.log(value.length);
};

printLen('Hello'); // 5
printLen([1, 2, 3]); // 3
printLen({ length: 10 }); // 10
printLen(123); // Invalid
