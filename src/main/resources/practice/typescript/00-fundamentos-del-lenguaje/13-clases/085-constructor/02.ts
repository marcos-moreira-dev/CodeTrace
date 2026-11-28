// Tema: constructor.
// Un parámetro por defecto permite omitir el último argumento; no se necesita una sobrecarga si la firma expresa la misma conducta.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 02.ts
// Ejecutar: node 02.js
// Salida esperada:
// m f

type Sex = 'm' | 'f';
class Person {
    constructor(public name: string,public age: number,public sex: Sex = 'm') {}
}
const p1 = new Person('Simon',17);
const p2 = new Person('Alice',22,'f');
console.log(p1.sex,p2.sex);
