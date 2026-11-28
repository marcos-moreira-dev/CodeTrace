// Tema: strictNullChecks
// Con strict, null no es asignable a string; la firma debe declarar explícitamente la ausencia y comprobarla.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 strict_null_checks.ts
// Diagnóstico esperado:
// El compilador debe rechazar el ejemplo (TS2322). No ejecutar.

const nombre: string = null;
