// Tema:
// La asignabilidad depende de las propiedades, los parámetros y retornos de las funciones, y
// las restricciones de miembros privados o protegidos. Los ejemplos muestran asignaciones
// aceptadas y rechazadas.

function x(a: string): void;
function x(a: string, b: number): void;
function x(a: string, b?: number): void {
    console.log(a, b);
}
x('a'); // Valid
x('a', 1); // Valid

function y(a: string): void; // Invalid, not compatible with implementation signature
function y(a: string, b: number): void;
function y(a: string, b: number): void {
    console.log(a, b);
}
y('a');
y('a', 1);
