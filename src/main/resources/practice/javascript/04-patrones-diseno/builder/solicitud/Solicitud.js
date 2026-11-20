// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar en COPIA: node --check Solicitud.js
// Ejecutar: node main.js desde esta carpeta, sin librerías externas

/**
 * Builder: configuración por etapas con validación al terminar.
 *
 * Object.freeze protege el producto también en tiempo de ejecución.
 * JavaScript no dispone del control de tipos compile-time de TS.
 */
class SolicitudBuilder{
    constructor(){
        this.rutaValor="";
        this.metodoValor="GET";
        this.cabeceras=[];
    }
    ruta(v){this.rutaValor=v;return this;}
    metodo(v){this.metodoValor=v;return this;}
    cabecera(v){this.cabeceras.push(v);return this;}
    construir(){
        if(!this.rutaValor.startsWith("/")||!this.metodoValor)
            throw Error("Solicitud");
        return Object.freeze({
            ruta:this.rutaValor,metodo:this.metodoValor,
            cabeceras:Object.freeze([...this.cabeceras])
        });
    }
}
module.exports={SolicitudBuilder};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\04-patrones-diseno\builder\solicitud\main.js
 * Nota: Solicitud.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   POST /facturas (1 cabecera)
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
