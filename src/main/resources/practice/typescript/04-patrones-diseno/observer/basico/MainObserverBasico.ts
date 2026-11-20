// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar desde la carpeta (PowerShell): tsc --strict --target ES2022 --module commonjs --noEmit observador.ts MainObserverBasico.ts
// Compilar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-observer" observador.ts MainObserverBasico.ts
// Ejecutar: node "$env:TEMP\ct-ts-observer\MainObserverBasico.js"

import { Observador } from "./observador";

/**
 * Cliente del patrón Observer basado en callbacks tipados.
 *
 * La clase Observador es el emisor: conoce una colección de funciones,
 * pero no sabe por qué cada suscriptor se interesa por los cambios.
 * El cliente incorpora dos listeners, actualiza el estado y comprueba
 * que ambos fueron notificados. La implementación es síncrona y local.
 *
 * En TypeScript una función (n: number) => void es también un contrato;
 * no hay necesidad de crear una clase por cada listener.
 */
const sensor = new Observador();
const eventos: string[] = [];
sensor.suscribir(n => eventos.push("pantalla:" + n));
sensor.suscribir(n => eventos.push("auditoria:" + n));
sensor.actualizar(42);
console.log(eventos);
if (eventos.length !== 2) throw Error("Observer no notificó");

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
