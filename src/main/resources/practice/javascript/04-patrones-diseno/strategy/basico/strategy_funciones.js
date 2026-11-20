// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Compilar/Comprobar: node --check 04-patrones-diseno/strategy/basico/strategy_funciones.js
// Ejecutar: node 04-patrones-diseno/strategy/basico/strategy_funciones.js

/**
 * Cliente Strategy con funciones de primera clase.
 * En JavaScript no es necesario crear una jerarquía de clases para
 * intercambiar reglas. Las funciones cumplen el contrato implícito;
 * un closure puede guardar el porcentaje de descuento.
 */
const normal = total => total;
const promocion = tasa => total => total * (1 - tasa);
function cobrar(subtotal, estrategia) { return estrategia(subtotal); }
console.log(cobrar(100, normal), cobrar(100, promocion(0.15)));
