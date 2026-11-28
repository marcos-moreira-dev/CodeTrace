// Tema:
// never representa ausencia de valores posibles y ayuda a describir funciones que no retornan
// y ramas inalcanzables.

const throwError = (message: string): never => {
    throw new Error(message);
};
