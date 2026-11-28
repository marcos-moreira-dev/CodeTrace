// Tema:
// try y catch permiten manejar excepciones. El valor capturado puede necesitar una
// comprobación antes de acceder a sus propiedades.

try {
    // Code that might throw different types of errors
} catch (error) {
    if (error instanceof TypeError) {
        // Handle TypeError
    } else if (error instanceof RangeError) {
        // Handle RangeError
    } else {
        // Handle other errors
    }
}
