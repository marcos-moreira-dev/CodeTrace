// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check ".\01-fundamentos\generadores-iteradores\generadores_iteradores.js"
// Ejecutar: node ".\01-fundamentos\generadores-iteradores\generadores_iteradores.js"

/**
 * Cliente de iteradores y funciones generadoras.
 *
 * yield permite producir elementos de uno en uno sin guardar
 * de antemano toda la secuencia. El protocolo de iteración
 * usa next() y devuelve {value,done}; for...of lo consume
 * automáticamente. Ideal para secuencias grandes o perezosas.
 */
function* fibonacci(limite) {
    let a = 0, b = 1;
    for (let i = 0; i < limite; i++) {
        yield a;
        [a, b] = [b, a + b];
    }
}
const iterador = fibonacci(7);
console.log([...iterador]);
