// Tema: Compatibilidad estructural y borrado
// Una variable con propiedades adicionales satisface el contrato si contiene prop1 del tipo requerido. La función lee únicamente prop1.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// Origin

type NameProp1 = { prop1: string };
function log(x: NameProp1): void { console.log(x.prop1); }
const obj = { prop2: 123, prop1: 'Origin' };
log(obj);
