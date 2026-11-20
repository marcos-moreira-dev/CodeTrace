// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar (PowerShell, copia del paquete): tsc --strict --target ES2022 --module commonjs --noEmit '.\04-patrones-diseno\decorator\notificaciones\decorator_notificaciones.ts'
// Ejecutar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts" '.\04-patrones-diseno\decorator\notificaciones\decorator_notificaciones.ts'; node "$env:TEMP\ct-ts\decorator_notificaciones.js"

/**
 * Cliente Decorator: añadir funciones sin modificar el objeto original.
 *
 * La notificación puede envolverse por varias capas con el mismo
 * contrato. En vez de heredar por cada combinación, se delega a
 * otro Notificador recibido en el constructor.
 */
interface Notificador { enviar(mensaje: string): void; }
class Consola implements Notificador {
    enviar(mensaje: string): void { console.log("Enviado:", mensaje); }
}
class Prefijo implements Notificador {
    constructor(private readonly interno: Notificador,
        private readonly etiqueta: string) {}
    enviar(mensaje: string): void { this.interno.enviar(this.etiqueta + mensaje); }
}
const aviso: Notificador = new Prefijo(new Consola(), "[INFO] ");
aviso.enviar("Servicio activo");
