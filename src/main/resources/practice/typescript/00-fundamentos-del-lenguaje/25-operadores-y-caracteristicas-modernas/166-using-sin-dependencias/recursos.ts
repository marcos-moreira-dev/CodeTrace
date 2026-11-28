// Tema: using sin dependencias.
// using libera los recursos en orden inverso; requiere soporte de Symbol.dispose.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 --lib ES2022,DOM,ESNext.Disposable recursos.ts
// Ejecutar: node recursos.js
// Salida esperada:
// trabajo
// B
// A

class Recurso implements Disposable { constructor(private nombre:string){} [Symbol.dispose](){console.log(this.nombre);} }
{using a=new Recurso("A");using b=new Recurso("B");console.log("trabajo");}
