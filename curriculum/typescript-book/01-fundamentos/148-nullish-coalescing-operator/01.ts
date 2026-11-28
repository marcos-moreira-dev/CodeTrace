// Tema:
// ?? selecciona el valor alternativo únicamente si el operando izquierdo es null o undefined.

const foo = null ?? 'foo';
console.log(foo); // foo

const baz = 1 ?? 'baz';
const baz2 = 0 ?? 'baz';
console.log(baz); // 1
console.log(baz2); // 0
