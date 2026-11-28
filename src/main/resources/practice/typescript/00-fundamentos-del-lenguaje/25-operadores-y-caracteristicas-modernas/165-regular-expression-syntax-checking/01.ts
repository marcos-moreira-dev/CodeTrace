// Tema: regular expression syntax checking.
// La referencia a un grupo inexistente se diagnostica durante la compilación de una expresión regular literal.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Diagnóstico esperado: TS1533; no ejecutar.

let r = /(a)\2/; // Error: This backreference refers to a group that does not exist.
