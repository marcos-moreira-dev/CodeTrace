// Tema: using declaration and explicit resource management.
// La liberación ocurre en orden inverso a la adquisición, incluso con varios recursos en un bloque.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 --lib ES2022,DOM,ESNext.Disposable 05.ts
// Ejecutar: node 05.js
// Salida esperada:
// C
// B
// A

function recurso(nombre: string): Disposable { return { [Symbol.dispose]() { console.log(nombre); } }; }
{
    using a = recurso("A"), b = recurso("B");
    using c = recurso("C");
}
