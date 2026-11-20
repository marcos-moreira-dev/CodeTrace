// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: node "$env:TEMP\ct-ts-course\MainAbstractFactory.js"

import { Claro, Oscuro, type Fabrica } from "./Familias";
/**
 * Cliente Abstract Factory: familia de DOS productos coordinados.
 *
 * Factory Method cambia la construcción de UN producto mediante
 * un creador; aquí cambiar de fábrica cambia todas las piezas
 * relacionadas. Las interfaces desaparecen tras compilar a JS.
 */
function pantalla(f:Fabrica):string{return f.boton().pintar()+" / "+f.dialogo().pintar();}
const a=pantalla(new Claro()),b=pantalla(new Oscuro());
if(a===b||!a.includes("clara")||!b.includes("oscura"))throw Error("Familias");
console.log(a,b);

/*
 * Salida esperada de consola (una línea; console.log(a, b)):
 *   [ OK ] / Ventana clara < OK > / Ventana oscura
 * Los argumentos se separan mediante un espacio en Node.js.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ejemplo-ts" .\04-patrones-diseno\abstract-factory\tema-consola\MainAbstractFactory.ts
 *   node "$env:TEMP\ct-ejemplo-ts\MainAbstractFactory.js"
 * Salida esperada:
 *   [ OK ] / Ventana clara < OK > / Ventana oscura
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
