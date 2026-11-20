// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: node "$env:TEMP\ct-ts-course\MainTemplateMethod.js"

import { ProcesoReporte, ReporteTexto, ReporteCsv } from "./Reportes";
/**
 * Cliente Template Method: usar un algoritmo fijo con pasos variables.
 *
 * Strategy recibe una política; Template Method la especializa por
 * herencia. En TS los métodos abstractos se verifican estáticamente
 * y se convierten a métodos normales en JavaScript.
 */
const a:ProcesoReporte=new ReporteTexto(),b:ProcesoReporte=new ReporteCsv();
if(!a.ejecutar("ventas",15).includes("Total: 15")||
   !b.ejecutar("ventas",15).includes("ventas,15"))throw Error("Reportes");
console.log((a.ejecutar("ventas",15)+b.ejecutar("ventas",15)).trimEnd());

/*
 * Salida esperada de consola (cuatro líneas):
 *   == ventas ==
 *   Total: 15
 *   titulo,total
 *   ventas,15
 * El método plantilla fija validación y pasos; las subclases aportan el formato.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\04-patrones-diseno\template-method\reportes\MainTemplateMethod.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainTemplateMethod.js"
 * Salida esperada:
 *   == ventas ==
 *   Total: 15
 *   titulo,total
 *   ventas,15
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
