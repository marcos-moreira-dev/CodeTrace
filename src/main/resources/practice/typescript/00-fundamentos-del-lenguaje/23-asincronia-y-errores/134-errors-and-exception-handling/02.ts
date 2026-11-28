// Tema: errors and exception handling.
// instanceof permite distinguir el tipo real de una excepción.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 02.ts
// Ejecutar: node 02.js
// Salida esperada:
// TypeError

try { throw new TypeError("tipo"); } catch (error) {
    if (error instanceof TypeError) console.log("TypeError");
    else if (error instanceof RangeError) console.log("RangeError");
    else console.log("otro error");
}
