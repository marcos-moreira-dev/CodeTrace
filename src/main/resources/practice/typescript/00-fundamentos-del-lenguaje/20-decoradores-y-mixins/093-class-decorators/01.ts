// Tema: class decorators.
// Un decorador de clase estándar puede devolver una subclase conservando el constructor; context identifica la clase.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 01.ts
// Ejecutar: node 01.js
// Salida esperada:
// Person
// Ana

type Constructor<T = object> = new (...args: any[]) => T;
function registrar<C extends Constructor>(Value: C, context: ClassDecoratorContext<C>) {
    return class extends Value { constructor(...args: any[]) { super(...args); console.log(context.name); } };
}
@registrar
class Person { constructor(public name: string) {} }
const p = new Person("Ana");
console.log(p.name);
