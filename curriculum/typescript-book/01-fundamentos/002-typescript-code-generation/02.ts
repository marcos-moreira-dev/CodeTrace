// Tema:
// La comprobación de tipos y la emisión de JavaScript son procesos distintos. Las interfaces y
// anotaciones se borran, mientras que las clases y propiedades reales pueden existir en
// ejecución.

'use strict';
const add = (a, b) => a + b;
const result = add('x', 'y'); // xy
