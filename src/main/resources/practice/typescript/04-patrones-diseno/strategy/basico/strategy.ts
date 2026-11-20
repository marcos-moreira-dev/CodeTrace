// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar desde COPIA de esta unidad: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Compilar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-unit" *.ts
// Ejecutar: desde el cliente Main... de la misma unidad

/** Contrato Strategy; también admite funciones en lugar de clases. */
export interface Descuento { aplicar(total: number): number; }

export class DescuentoFijo implements Descuento {
    constructor(private readonly porcentaje: number) {
        if (porcentaje < 0 || porcentaje > 1) throw Error("Porcentaje");
    }
    aplicar(total: number): number { return total * (1 - this.porcentaje); }
}

export class Facturador {
    cobrar(total: number, estrategia: Descuento): number {
        if (total < 0) throw Error("Subtotal");
        return estrategia.aplicar(total);
    }
}
