// CodeTrace | Referencia: TypeScript 5.9 (ECMAScript ES2022; Node.js 24 para ejecutar JS).
// Comandos desde la carpeta del paquete, en una COPIA:
// Comprobar tipos sin generar archivos: tsc --noEmit --target ES2022 --module commonjs '.\01-sintaxis\control.ts'
// Instalar una vez el compilador si no existe: npm install -g typescript@5.9.3

function sumarPares(limite: number): number {
  let total = 0;
  for (let i = 0; i <= limite; i++) {
    if (i % 2 === 0) total += i;
  }
  return total;
}

console.log(sumarPares(10));
