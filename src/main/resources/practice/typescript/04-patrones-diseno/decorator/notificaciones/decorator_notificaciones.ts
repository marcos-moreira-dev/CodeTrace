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
