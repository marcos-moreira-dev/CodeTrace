// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar desde COPIA de esta unidad: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Compilar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-unit" *.ts
// Ejecutar: desde el cliente Main... de la misma unidad

/** Producto base: el creador no conoce su clase concreta. */
export interface Exportador { exportar(texto: string): string; }

class ExportadorTexto implements Exportador {
    exportar(texto: string): string { return "TXT: " + texto; }
}

/**
 * Factory Method GoF: la subclase creadora implementa crear().
 * preparar() define el flujo estable, independiente del producto.
 */
export abstract class Creador {
    protected abstract crear(): Exportador;
    preparar(texto: string): string { return this.crear().exportar(texto); }
}

export class CreadorTexto extends Creador {
    protected crear(): Exportador { return new ExportadorTexto(); }
}
