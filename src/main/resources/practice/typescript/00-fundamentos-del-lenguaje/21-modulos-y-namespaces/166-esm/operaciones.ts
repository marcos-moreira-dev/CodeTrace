// Tema: Exportación nombrada y exportación por defecto.
// Compilar: tsc --strict --target ES2022 --module NodeNext main.ts operaciones.ts
// Ejecutar: node main.js
// Salida esperada: 6 5
export const doble=(n:number)=>n*2;
export default function suma(a:number,b:number):number{return a+b;}
