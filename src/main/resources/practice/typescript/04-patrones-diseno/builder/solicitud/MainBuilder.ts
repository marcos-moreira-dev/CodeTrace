// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: node "$env:TEMP\ct-ts-course\MainBuilder.js"

import { SolicitudBuilder, type Solicitud } from "./Solicitud";
/**
 * Cliente Builder: separar pasos de construcción del resultado final.
 *
 * TypeScript readonly impide ciertas mutaciones a nivel de tipo, pero
 * no necesariamente en JS. La implementación usa Object.freeze para
 * expresar también la intención inmutable en tiempo de ejecución.
 */
const solicitud:Solicitud=new SolicitudBuilder().ruta("/facturas")
    .metodo("POST").cabecera("Content-Type: application/json").construir();
if(solicitud.ruta!=="/facturas"||solicitud.cabeceras.length!==1)
    throw Error("Builder");
console.log(solicitud.metodo+" "+solicitud.ruta+" ("+solicitud.cabeceras.length+" cabecera)");

/*
 * Salida esperada de consola:
 *   POST /facturas (1 cabecera)
 * La validación del Builder entrega un producto con una cabecera.
 */
