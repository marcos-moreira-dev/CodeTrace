// Tema:
// Un parámetro de tipo const favorece la inferencia de tipos literales precisos al llamar a
// una función genérica.

function identity<const T>(value: T) {
    // Using const modifier on type parameters
    return value;
}
const values = identity({ a: 'a', b: 'b' }); // Type inferred is: { a: "a"; b: "b"; }
