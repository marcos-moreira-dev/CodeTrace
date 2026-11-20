// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check main.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

const {HashAbierto}=require("./HashAbierto");
/**
 * Cliente hash abierto: forzar colisiones con dos casillas.
 *
 * Una clave nueva debe sondear otra celda, una repetida
 * actualiza su valor y la tabla llena rechaza la inserción.
 * Comparar con Map estándar y encadenamiento separado.
 */
function main(){
    const t=new HashAbierto(2);
    t.poner("A",1);t.poner("B",2);t.poner("A",3);
    if(t.buscar("A")!==3||t.buscar("B")!==2||
       t.buscar("Z")!==undefined||t.cantidad!==2)throw Error("Hash");
    let lleno=false;
    try{t.poner("C",4);}catch{lleno=true;}
    if(!lleno)throw Error("Saturación");
    console.log("Sondeo verificado");
}
if(require.main===module)main();

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\02-estructuras-datos\tabla-hash\direccionamiento-abierto\main.js
 * Salida esperada:
 *   Sondeo verificado
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
