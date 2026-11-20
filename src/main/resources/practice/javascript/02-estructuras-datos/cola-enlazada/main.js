// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check main.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

const {ColaEnlazada}=require("./ColaEnlazada");
/**
 * Cliente Queue enlazada: FIFO sin capacidad fija de arreglo.
 *
 * A diferencia de Array.shift(), desencolar no desplaza
 * elementos, solo actualiza referencias. Reiniciar último
 * al vaciar toda la cola evita corromper siguientes inserciones.
 */
function main(){
    const q=new ColaEnlazada();
    q.encolar("A");q.encolar("B");
    if(q.desencolar()!=="A"||q.desencolar()!=="B")throw Error("FIFO");
    q.encolar("C");
    if(q.desencolar()!=="C"||q.cantidad!==0)throw Error("Tail");
    console.log("Cola enlazada correcta");
}
if(require.main===module)main();

/*
 * Salida esperada de consola:
 *   Cola enlazada correcta
 *   (los valores variables completan los fragmentos anteriores)
 * Verificacion: termina sin excepciones inesperadas.
 */
