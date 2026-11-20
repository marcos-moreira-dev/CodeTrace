// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar desde COPIA de esta unidad: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Compilar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-unit" *.ts
// Ejecutar: node "$env:TEMP\ct-ts-unit\MainFactoryMethod.js"

import { Creador, CreadorTexto } from "./FactoryMethod";

/**
 * Cliente Factory Method: la construcción se delega a una subclase.
 *
 * A diferencia de la fábrica simple, aquí el contrato del creador
 * contiene la operación de negocio preparar() que llama a crear().
 * Se evita un switch de productos disperso en los clientes.
 */
const creador: Creador = new CreadorTexto();
const resultado = creador.preparar("Factura 001");
console.log(resultado);
if (resultado !== "TXT: Factura 001") throw Error("Factory Method");

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\04-patrones-diseno\factory-method\documentos\MainFactoryMethod.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainFactoryMethod.js"
 * Salida esperada:
 *   TXT: Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
