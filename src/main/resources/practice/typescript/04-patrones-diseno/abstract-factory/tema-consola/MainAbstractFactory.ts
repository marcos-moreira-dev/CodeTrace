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
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
