// Tema: Excepción y never
// Una función que siempre lanza una excepción no devuelve un valor normal. No se invoca en esta prueba estática.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 02.ts
// Salida esperada:
// Compila sin errores; es una comprobación de tipos, no un programa de consola.

const throwError = (message: string): never => {
    throw new Error(message);
};
