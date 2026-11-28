// Tema: .cts emite CommonJS (.cjs).
// Compilar: tsc --strict --target ES2022 --module NodeNext main.cts operaciones.cts
// Ejecutar: node main.cjs
// Salida esperada: 8
export function doble(n:number):number{return n*2;}
