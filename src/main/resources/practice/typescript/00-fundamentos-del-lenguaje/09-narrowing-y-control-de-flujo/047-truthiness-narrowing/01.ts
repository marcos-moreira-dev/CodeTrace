// Tema: truthiness narrowing.
// Una comprobación de verdad descarta determinados valores falsos; debe considerarse que cero y la cadena vacía también son falsos.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada: compila sin diagnósticos; ejercicio de comprobación estática.

const toUpperCase = (name: string | null) => {
    if (name) {
        return name.toUpperCase();
    } else {
        return null;
    }
};
