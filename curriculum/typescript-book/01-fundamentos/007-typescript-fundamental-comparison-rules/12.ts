// Tema:
// La asignabilidad depende de las propiedades, los parámetros y retornos de las funciones, y
// las restricciones de miembros privados o protegidos. Los ejemplos muestran asignaciones
// aceptadas y rechazadas.

enum X {
    A,
    B,
}
enum Y {
    A,
    B,
    C,
}
const xa: number = X.A; // Valid
const ya: Y = 0; // Valid
X.A === Y.A; // Invalid
