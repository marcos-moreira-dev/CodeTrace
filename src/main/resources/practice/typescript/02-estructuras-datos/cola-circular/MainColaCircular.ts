// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node 24
// Compilar/Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit ARCHIVO.ts
// Ejecutar: node dist/MainColaCircular.js

import { ColaCircular } from "./ColaCircular";
/**
 * Cliente de cola FIFO circular.
 * El arreglo reutiliza posiciones mediante módulo. El cliente no necesita
 * conocer los índices internos. Separamos contrato, implementación y uso
 * para que la relación entre archivos pueda estudiarse en CodeTrace.
 *
 * Compilar desde la carpeta: tsc --strict --target ES2022 --module commonjs --outDir dist *.ts
 * Ejecutar: node dist/MainColaCircular.js
 */
const cola = new ColaCircular<string>(3);
cola.encolar("reporte");
cola.encolar("correo");
console.log(cola.extraer());
cola.encolar("factura");
console.log(cola.extraer(), cola.extraer());

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\02-estructuras-datos\cola-circular\MainColaCircular.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainColaCircular.js"
 * Salida esperada:
 *   reporte
 *   correo factura
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
