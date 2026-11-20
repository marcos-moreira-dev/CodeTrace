// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar en COPIA: node --check Comandos.js
// Ejecutar: node main.js desde esta carpeta, sin librerías externas

/**
 * Command: un objeto con ejecutar/deshacer y su propio estado.
 * A diferencia de una función callback pura, recuerda el texto
 * anterior para que el receptor pueda restaurarse.
 */
class Editor{
    constructor(){this.texto="";}
    agregar(parte){this.texto+=parte;}
    restaurar(antes){this.texto=antes;}
}
class AgregarTexto{
    constructor(editor,parte){
        this.editor=editor;this.parte=parte;this.anterior=undefined;
    }
    ejecutar(){
        if(this.anterior!==undefined)throw Error("Ya ejecutado");
        this.anterior=this.editor.texto;
        this.editor.agregar(this.parte);
    }
    deshacer(){
        if(this.anterior===undefined)throw Error("No ejecutado");
        this.editor.restaurar(this.anterior);
        this.anterior=undefined;
    }
}
module.exports={Editor,AgregarTexto};
