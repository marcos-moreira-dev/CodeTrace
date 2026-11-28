// Tema:
// NoInfer impide que una posición concreta contribuya a la inferencia de un parámetro de tipo.

// Automatic inference of types within the scope of a generic function.
function fn<T extends string>(x: T[], y: T) {
    return x.concat(y);
}
const r = fn(['a', 'b'], 'c'); // Type here is ("a" | "b" | "c")[]
