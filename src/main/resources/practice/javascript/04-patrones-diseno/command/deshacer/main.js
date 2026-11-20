// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar en COPIA: node --check main.js
// Ejecutar: node main.js desde esta carpeta, sin librerías externas

const {Editor,AgregarTexto}=require("./Comandos");
/**
 * Cliente Command con historial de operaciones reversibles.
 *
 * El invocador mantiene una pila de objetos Comando. El receptor
 * Editor no tiene por qué conocer quién activó el comando: así se
 * separa la acción de un botón, menú, atajo o evento de consola.
 */
function main(){
    const editor=new Editor(),historia=[];
    for(const parte of ["Hola"," mundo"]){
        const c=new AgregarTexto(editor,parte);
        c.ejecutar();historia.push(c);
    }
    if(editor.texto!=="Hola mundo")throw Error("Ejecutar");
    historia.pop().deshacer();
    if(editor.texto!=="Hola")throw Error("Undo");
    console.log(editor.texto);
}
if(require.main===module)main();
