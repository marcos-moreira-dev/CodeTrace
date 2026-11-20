// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: node "$env:TEMP\ct-ts-course\MainComposite.js"

import { Archivo, Carpeta, type Elemento } from "./Elementos";
/**
 * Cliente Composite: archivos y carpetas consumidos uniformemente.
 *
 * No hay un switch por clase concreta. El árbol permite anidar
 * carpetas; los ciclos arbitrarios necesitarían validación general
 * adicional para no provocar recursión infinita.
 */
const raiz=new Carpeta("proyecto"),src=new Carpeta("src");
src.agregar(new Archivo("Main.ts",120));src.agregar(new Archivo("App.ts",80));
raiz.agregar(src);raiz.agregar(new Archivo("README.md",20));
const modelo:Elemento=raiz;
if(modelo.bytes()!==220)throw Error("Composite");
console.log("Bytes:",modelo.bytes());

/*
 * Salida esperada de consola:
 *   Bytes:
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
