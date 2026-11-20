// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar desde COPIA de esta unidad: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Compilar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-unit" *.ts
// Ejecutar: node "$env:TEMP\ct-ts-unit\MainFactory.js"

import { crearNotificacion, type Notificacion } from "./factory";

/**
 * Cliente de fábrica simple: aislar la creación del producto.
 *
 * El cliente invoca la fábrica y usa Notificacion, sin hacer
 * new Correo(). Contrasta con factory-method/documentos:
 * allí una jerarquía de creadores decide el producto.
 */
const mensaje: Notificacion = crearNotificacion();
const salida = mensaje.enviar("Listo");
if (salida !== "Correo: Listo") throw Error("Fábrica");
console.log(salida);

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
