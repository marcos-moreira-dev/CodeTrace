// Tema:
// Una comprobación de verdad descarta determinados valores falsos; debe considerarse que cero
// y la cadena vacía también son falsos.

const toUpperCase = (name: string | null) => {
    if (name) {
        return name.toUpperCase();
    } else {
        return null;
    }
};
