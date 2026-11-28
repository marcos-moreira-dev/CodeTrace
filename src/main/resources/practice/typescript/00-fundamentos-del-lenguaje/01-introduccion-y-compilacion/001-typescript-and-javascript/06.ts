// Tema: Operadores incompatibles
// Aunque JavaScript admite la coerción de boolean a number, el comprobador rechaza esta suma implícita.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 06.ts
// Diagnóstico esperado:
// El compilador debe rechazar el ejemplo (TS2365). No ejecutar.

const result = 1 + true;
