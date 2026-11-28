// Tema: errors and exception handling.
// catch recibe un valor desconocido; finally se ejecuta al abandonar el bloque, incluso tras una excepción.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// intento
// fallo
// fin

try {
    console.log("intento");
    throw new Error("fallo");
} catch (error) {
    if (error instanceof Error) console.log(error.message);
} finally { console.log("fin"); }
