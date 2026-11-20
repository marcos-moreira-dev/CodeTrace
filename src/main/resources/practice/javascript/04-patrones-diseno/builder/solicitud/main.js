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
    console.log(s);
}
if(require.main===module)main();

/*
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
