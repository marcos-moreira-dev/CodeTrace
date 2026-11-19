// CodeTrace | Referencia: TypeScript 5+, proyecto Observer
// Compilar: npx tsc --target ES2022 --module commonjs 07-proyectos/observer/*.ts
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
