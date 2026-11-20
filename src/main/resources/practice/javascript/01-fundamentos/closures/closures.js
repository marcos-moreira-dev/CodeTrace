// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Compilar/Comprobar: node --check ARCHIVO.js
// Ejecutar: node ARCHIVO.js

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
