// Tema:
// Un parámetro de tipo const favorece la inferencia de tipos literales precisos al llamar a
// una función genérica.

function identity<T>(value: T) {
    // No const here
    return value;
}
const values = identity({ a: 'a', b: 'b' }); // Type inferred is: { a: string; b: string; }
