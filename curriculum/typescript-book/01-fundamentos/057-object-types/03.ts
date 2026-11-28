// Tema:
// Un tipo de objeto describe las propiedades y métodos que debe satisfacer una estructura.

const sum = (x: { a: number; b: number }) => x.a + x.b;
console.log(sum({ a: 5, b: 1 }));
