// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Comprobar desde COPIA de esta unidad: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Compilar: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-unit" *.ts
// Ejecutar: node "$env:TEMP\ct-ts-unit\MainAdapter.js"

import { Adaptador, Legado, type SensorCelsius } from "./adapter_unidades";

/**
 * Cliente Adapter: el dominio habla Celsius, la API antigua Fahrenheit.
 *
 * El cliente depende de una interfaz propia que se comprueba al compilar.
 * Las interfaces TypeScript se borran al generar JavaScript, mientras la
 * conversión de unidades sigue existiendo en tiempo de ejecución.
 */
const sensor: SensorCelsius = new Adaptador(new Legado());
if (sensor.leer() !== 25) throw Error("Conversión");
console.log("Celsius:", sensor.leer());

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\04-patrones-diseno\adapter\unidades\MainAdapter.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainAdapter.js"
 * Salida esperada:
 *   Celsius: 25
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
