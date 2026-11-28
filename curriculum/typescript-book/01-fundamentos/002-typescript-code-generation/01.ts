// Tema:
// La comprobación de tipos y la emisión de JavaScript son procesos distintos. Las interfaces y
// anotaciones se borran, mientras que las clases y propiedades reales pueden existir en
// ejecución.

const add = (a: number, b: number): number => a + b;
const result = add('x', 'y'); // Argument of type 'string' is not assignable to parameter of type 'number'.
