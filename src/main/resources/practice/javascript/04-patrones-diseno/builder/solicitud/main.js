// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar en COPIA: node --check main.js
// Ejecutar: node main.js desde esta carpeta, sin librerías externas

const {SolicitudBuilder}=require("./Solicitud");
/**
 * Cliente Builder: distinguir el constructor mutable del
 * producto final inmutable. Es útil con opciones y reglas de
 * validación; no se justifica para objetos con pocos campos.
 *
 * Object.freeze complementa el contrato, que en JS es dinámico.
 */
function main(){
    const s=new SolicitudBuilder().ruta("/facturas").metodo("POST")
        .cabecera("Content-Type: application/json").construir();
    if(s.ruta!=="/facturas"||s.cabeceras.length!==1)throw Error("Builder");
    console.log(s.metodo+" "+s.ruta+" ("+s.cabeceras.length+" cabecera)");
}
if(require.main===module)main();

/*
 * Salida esperada de consola:
 *   POST /facturas (1 cabecera)
 * La validación del Builder entrega un producto con una cabecera.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\04-patrones-diseno\builder\solicitud\main.js
 * Salida esperada:
 *   POST /facturas (1 cabecera)
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
