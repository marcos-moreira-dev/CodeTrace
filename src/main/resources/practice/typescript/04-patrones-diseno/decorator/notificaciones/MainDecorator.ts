// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar desde COPIA de esta unidad: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Compilar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-unit" *.ts
// Ejecutar: node "$env:TEMP\ct-ts-unit\MainDecorator.js"

import { Consola, Prefijo, type Notificador } from "./decorator_notificaciones";

/**
 * Cliente Decorator: cada capa tiene el mismo contrato y delega.
 *
 * El decorador introduce un prefijo sin alterar Consola. En vez
 * de tener subclases por todas las combinaciones, componer objetos
 * facilita ampliar la funcionalidad bajo un contrato estable.
 */
const emisor: Notificador = new Prefijo(new Consola(), "[INFO] ");
emisor.enviar("Servicio activo");

/*
 * Salida esperada de consola:
 *   Sin salida de texto; las pruebas del cliente se cumplen.
 * Verificacion: termina sin excepciones inesperadas.
 */
