// CodeTrace | Referencia: TypeScript 5.9 / ES2022 / Node.js 24
// Compilar en COPIA desde esta carpeta: tsc --strict --target ES2022 --module commonjs --outDir "$env:TEMP\ct-ts-course" *.ts
// Comprobar: tsc --strict --target ES2022 --module commonjs --noEmit *.ts
// Ejecutar: node "$env:TEMP\ct-ts-course\MainCommand.js"

import { AgregarTexto, Editor, type Comando } from "./Comandos";
/**
 * Cliente Command con historial LIFO; cada comando conoce ejecutar
 * y deshacer, mientras el editor conoce el estado real.
 *
 * A diferencia de un callback sin estado, un comando puede almacenar
 * lo necesario para restaurar el estado previo.
 */
const editor=new Editor(),historial:Comando[]=[];
for(const parte of ["Hola"," mundo"]){
    const c=new AgregarTexto(editor,parte);
    c.ejecutar();historial.push(c);
}
if(editor.texto!=="Hola mundo")throw Error("Ejecutar");
historial.pop()!.deshacer();
if(String(editor.texto)!=="Hola")throw Error("Deshacer");
console.log(editor.texto);
