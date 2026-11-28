// Tema: Función que no retorna
// Un bucle infinito no termina normalmente y puede tener tipo never. Solo se comprueba el tipo, no se invoca.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Salida esperada:
// Compila sin errores; es una comprobación de tipos, no un programa de consola.

const infiniteLoop = (): never => {
    while (true) { /* Nunca retorna. */ }
};
