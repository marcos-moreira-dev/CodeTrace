// Tema: Función exportada que permite consultar su firma desde otro módulo.
// Compilar: tsc --strict --target ES2022 --module NodeNext 01.ts calc.ts
// Ejecutar: node 01.js
// Salida esperada: 3
export const add = (x:number,y:number):number => x+y;
