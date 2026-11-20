// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Compilar/Comprobar: node --check 01-fundamentos/closures/closures.js
// Ejecutar: node 01-fundamentos/closures/closures.js

/**
 * Cliente de closures: las funciones conservan las variables de su
 * entorno léxico. Sirve para encapsular estado sin clases ni globales.
 * Cada contador obtenido posee un estado independiente.
 */
function contador() {
    let valor = 0;
    return () => ++valor;
}
const uno = contador(), otro = contador();
console.log(uno(), uno(), otro(), uno());

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\01-fundamentos\closures\closures.js
 * Salida esperada:
 *   1 2 1 3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
