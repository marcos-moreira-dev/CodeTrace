// CodeTrace | Referencia: TypeScript 5+, proyecto Observer
// Compilar: npx tsc --target ES2022 --module commonjs 04-patrones-diseno/observer/*.ts
import { BusEventos } from "./BusEventos";
const bus = new BusEventos();
bus.registrar(evento => console.log(evento.tipo, evento.detalle));
bus.emitir({ tipo: "venta", detalle: "Factura 001" });

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\04-patrones-diseno\observer\MainObserver.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainObserver.js"
 * Salida esperada:
 *   venta Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
