// CodeTrace | Referencia: TypeScript 5.9, compilación objetivo ES2022.
// Desde PowerShell en una COPIA de la carpeta typescript:
// Tipos/sintaxis: tsc --noEmit --target ES2022 --module commonjs '.\06-patrones\factory.ts'
// Instalación del compilador si falta: npm install -g typescript@5.9.3
// Estos módulos pueden no tener punto de entrada; no necesitan ejecutarse.

interface Notificacion {
  enviar(mensaje: string): string;
}

class Correo implements Notificacion {
  enviar(mensaje: string): string {
    return "Correo: " + mensaje;
  }
}

function crearNotificacion(): Notificacion {
  return new Correo();
}

console.log(crearNotificacion().enviar("Listo"));
