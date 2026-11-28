// Tema:
// TypeScript comprueba tipos durante la compilación; JavaScript ejecuta los valores. Las
// anotaciones ayudan a detectar operaciones o propiedades incompatibles.

const items = [{ x: 1 }, { x: 2 }];
const result = items.filter(item => item.y);
