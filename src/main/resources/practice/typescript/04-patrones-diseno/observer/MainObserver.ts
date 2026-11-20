// CodeTrace | Referencia: TypeScript 5+, proyecto Observer
// Compilar: npx tsc --target ES2022 --module commonjs 04-patrones-diseno/observer/*.ts
import { BusEventos } from "./BusEventos";
const bus = new BusEventos();
bus.registrar(evento => console.log(evento.tipo, evento.detalle));
bus.emitir({ tipo: "venta", detalle: "Factura 001" });

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
