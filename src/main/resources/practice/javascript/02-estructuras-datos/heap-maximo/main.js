// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar desde COPIA: node --check main.js
// Ejecutar: node main.js desde el cliente de esta carpeta
// Módulos locales CommonJS, sin instalar paquetes npm.

const {HeapMaximo}=require("./HeapMaximo");
/**
 * Cliente Max heap: propiedad padre>=hijos, no orden completo.
 *
 * Extraer sucesivamente genera el orden descendente, pero
 * la estructura interna solo garantiza un máximo en su raíz.
 * Se comparan operaciones con heap mínimo y cola de prioridad.
 */
function main(){
    const h=new HeapMaximo();
    [4,1,9,7,2].forEach(x=>h.insertar(x));
    if(Array.from({length:5},()=>h.extraer()).join(",")!=="9,7,4,2,1")
        throw Error("Heap");
    console.log("Max heap correcto");
}
if(require.main===module)main();

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\02-estructuras-datos\heap-maximo\main.js
 * Salida esperada:
 *   Max heap correcto
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
