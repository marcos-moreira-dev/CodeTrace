// Tema:
// La asignabilidad depende de las propiedades, los parámetros y retornos de las funciones, y
// las restricciones de miembros privados o protegidos. Los ejemplos muestran asignaciones
// aceptadas y rechazadas.

type X = (a: number) => undefined;
type Y = (a: number) => number;
let x: X = (a: number) => undefined;
let y: Y = (a: number) => 1;
y = x; // Invalid
x = y; // Invalid
