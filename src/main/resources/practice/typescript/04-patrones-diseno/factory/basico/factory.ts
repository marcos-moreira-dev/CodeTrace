// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar desde COPIA de esta unidad: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Compilar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-unit" *.ts
// Ejecutar: desde el cliente Main... de la misma unidad

/**
 * Fábrica simple de objetos detrás de un contrato.
 *
 * No equivale a Factory Method GoF: una sola función conoce la
 * construcción de la clase concreta. Se requiere menos jerarquía.
 */
export interface Notificacion { enviar(mensaje: string): string; }

class Correo implements Notificacion {
    enviar(mensaje: string): string { return "Correo: " + mensaje; }
}

export function crearNotificacion(): Notificacion { return new Correo(); }

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\04-patrones-diseno\factory\basico\MainFactory.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainFactory.js"
 * Nota: factory.ts es un componente; la salida corresponde al cliente MainFactory.ts.
 * Salida esperada:
 *   Correo: Listo
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
