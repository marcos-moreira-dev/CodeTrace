// Tema: new target meta property.
// new.target identifica el constructor que inició la creación, incluso dentro del constructor de la clase padre.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// Parent
// Child

class Parent { constructor() { console.log(new.target.name); } }
class Child extends Parent { constructor() { super(); } }
new Parent();
new Child();
