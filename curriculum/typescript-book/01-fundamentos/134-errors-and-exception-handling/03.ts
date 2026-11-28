// Tema:
// try y catch permiten manejar excepciones. El valor capturado puede necesitar una
// comprobación antes de acceder a sus propiedades.

class CustomError extends Error {
    constructor(message: string) {
        super(message);
        this.name = 'CustomError';
    }
}

throw new CustomError('This is a custom error.');
