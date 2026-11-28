// Tema: object y tipos que aceptan primitivos
// object excluye primitivos, mientras {} y Object aceptan valores no nulos. La mayúscula Object no es un sustituto de object.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 object_y_primitivos.ts
// Diagnóstico esperado:
// El compilador debe rechazar el ejemplo (TS2322). No ejecutar.

const a: {} = 7;
const b: Object = 'Ana';
const c: object = 7;
