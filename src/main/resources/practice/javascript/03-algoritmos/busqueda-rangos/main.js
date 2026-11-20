// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar en COPIA: node --check main.js
// Ejecutar: node main.js (desde esta carpeta)

const {lower,upper}=require("./LimitesOrdenados");
/**
 * Cliente lower/upper bound: la posicion de insercion pertenece
 * al rango [0,n]. Precondicion arreglo ya ordenado ascendente.
 */
function main(){
    const a=[1,3,3,3,5,9];
    const lo=lower(a,3),hi=upper(a,3),pos=lower(a,4);
    if(lo!==1||hi!==4||pos!==4||upper(a,4)!==4)throw Error("Rangos");
    console.log("Valor 3: ["+lo+", "+hi+")");
    console.log("Insertar 4 en indice: "+pos);
}
if(require.main===module)main();
/*
 * Salida esperada:
 * Valor 3: [1, 4)
 * Insertar 4 en indice: 4
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\03-algoritmos\busqueda-rangos\main.js
 * Salida esperada:
 *   Valor 3: [1, 4)
 *   Insertar 4 en indice: 4
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
