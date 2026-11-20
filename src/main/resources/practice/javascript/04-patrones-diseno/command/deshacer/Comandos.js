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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\04-patrones-diseno\command\deshacer\main.js
 * Nota: Comandos.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   Hola
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
