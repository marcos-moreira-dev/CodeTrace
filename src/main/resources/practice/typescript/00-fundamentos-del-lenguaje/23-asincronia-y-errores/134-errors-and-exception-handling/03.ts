// Tema: errors and exception handling.
// Una excepción propia especializa Error y conserva su mensaje. El cliente captura el error previsto.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 03.ts
// Ejecutar: node 03.js
// Salida esperada:
// CustomError: fallo propio

class CustomError extends Error {
    constructor(message: string) { super(message); this.name = "CustomError"; }
}
try { throw new CustomError("fallo propio"); } catch(error) {
    if (error instanceof CustomError) console.log(error.name + ": " + error.message);
}
