// Tema: decoradores estandar.
// El decorador estándar de campo retorna un inicializador; transforma el valor al crear la instancia.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 decorador.ts
// Ejecutar: node decorador.js
// Salida esperada:
// 0

function positivo<T>(_:undefined,context:ClassFieldDecoratorContext<T,number>) { return (n:number)=>Math.max(0,n); }
class Caja { @positivo valor=-5; }
console.log(new Caja().valor);
