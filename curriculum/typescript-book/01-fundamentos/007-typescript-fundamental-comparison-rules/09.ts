// Tema:
// La asignabilidad depende de las propiedades, los parámetros y retornos de las funciones, y
// las restricciones de miembros privados o protegidos. Los ejemplos muestran asignaciones
// aceptadas y rechazadas.

type X = (a: number, ...rest: number[]) => undefined;
let x: X = a => undefined; //valid
