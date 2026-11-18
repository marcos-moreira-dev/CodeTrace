// CodeTrace | Referencia: TypeScript 5.9 (ECMAScript ES2022; Node.js 24 para ejecutar JS).
// Comandos desde la carpeta del paquete, en una COPIA:
// Comprobar tipos sin generar archivos: tsc --noEmit --target ES2022 --module commonjs '.\02-tipos\narrowing.ts'
// Instalar una vez el compilador si no existe: npm install -g typescript@5.9.3

type Resultado =
  | { ok: true; valor: number }
  | { ok: false; error: string };

function mostrar(resultado: Resultado): string {
  if (resultado.ok) return String(resultado.valor);
  return resultado.error;
}
