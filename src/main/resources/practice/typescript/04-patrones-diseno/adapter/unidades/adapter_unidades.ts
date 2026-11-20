// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar desde COPIA de esta unidad: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Compilar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-unit" *.ts
// Ejecutar: desde el cliente Main... de la misma unidad

/** API requerida por el dominio, separada de la fuente externa. */
export interface SensorCelsius { leer(): number; }

/** Proveedor simulado: no tenemos permiso para modificarlo. */
export class Legado {
    fahrenheit(): number { return 77; }
}

/** Adapter implementa el contrato propio y traduce el de la API ajena. */
export class Adaptador implements SensorCelsius {
    constructor(private readonly antiguo: Legado) {}
    leer(): number { return (this.antiguo.fahrenheit() - 32) * 5 / 9; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\04-patrones-diseno\adapter\unidades\MainAdapter.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainAdapter.js"
 * Nota: adapter_unidades.ts es un componente; la salida corresponde al cliente MainAdapter.ts.
 * Salida esperada:
 *   Celsius: 25
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
