// Tema:
// never representa ausencia de valores posibles y ayuda a describir funciones que no retornan
// y ramas inalcanzables.

const infiniteLoop = (): never => {
    while (true) {
        // do something
    }
};
