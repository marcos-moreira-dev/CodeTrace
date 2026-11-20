// CodeTrace | Referencia: TypeScript 5.9 (ECMAScript ES2022; Node.js 24 para ejecutar JS).
// Comandos desde la carpeta del paquete, en una COPIA:
// Comprobar tipos sin generar archivos: tsc --noEmit --target ES2022 --module commonjs '.\01-sintaxis\variables.ts'
// Instalar una vez el compilador si no existe: npm install -g typescript@5.9.3

const nombre: string = "CodeTrace";
let archivos: number = 3;
const activo: boolean = true;

archivos += 2;
console.log(nombre, archivos, activo);
