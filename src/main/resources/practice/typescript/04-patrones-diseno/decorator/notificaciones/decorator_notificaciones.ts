// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar desde COPIA de esta unidad: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Compilar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-unit" *.ts
// Ejecutar: desde el cliente Main... de la misma unidad

/** Contrato común de componente y decoradores. */
export interface Notificador { enviar(mensaje: string): void; }

export class Consola implements Notificador {
    enviar(mensaje: string): void { console.log("Enviado:", mensaje); }
}

/** Añade comportamiento mediante composición, no mediante herencia masiva. */
export class Prefijo implements Notificador {
    constructor(private readonly interno: Notificador, private readonly etiqueta: string) {}
    enviar(mensaje: string): void { this.interno.enviar(this.etiqueta + mensaje); }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\04-patrones-diseno\decorator\notificaciones\MainDecorator.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainDecorator.js"
 * Nota: decorator_notificaciones.ts es un componente; la salida corresponde al cliente MainDecorator.ts.
 * Salida esperada:
 *   Enviado: [INFO] Servicio activo
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
