// Tema: Parámetros sin anotación
// Sin tipos ni contexto, el compilador no puede inferir los parámetros de la función. strict rechaza el any implícito.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 01.ts
// Diagnóstico esperado:
// El compilador debe rechazar el ejemplo (TS7006, TS7006). No ejecutar.

const sum = (a, b) => a + b;
