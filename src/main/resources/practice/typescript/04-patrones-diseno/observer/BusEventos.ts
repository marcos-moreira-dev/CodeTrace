// CodeTrace | Referencia: TypeScript 5+, proyecto Observer
// Compilar: npx tsc --target ES2022 --module commonjs 04-patrones-diseno/observer/*.ts
import type { Evento, Observador } from "./Evento";
export class BusEventos {
    private readonly observadores: Observador[] = [];
    registrar(observador: Observador): void {
        this.observadores.push(observador);
    }
    emitir(evento: Evento): void {
        for (const observador of this.observadores) observador(evento);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\04-patrones-diseno\observer\MainObserver.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainObserver.js"
 * Nota: BusEventos.ts es un componente; la salida corresponde al cliente MainObserver.ts.
 * Salida esperada:
 *   venta Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
