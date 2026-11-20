// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check main.js
// Ejecutar: node main.js (desde copia de esta carpeta)
// Módulos locales CommonJS sin dependencias de npm.

/** Strategy con funciones de primera clase: no se fuerza herencia. */
function conDescuento(porcentaje) {
    if (porcentaje < 0 || porcentaje > 1) throw RangeError("Porcentaje");
    return subtotal => subtotal * (1 - porcentaje);
}
function cobrar(subtotal, estrategia) {
    if (!Number.isFinite(subtotal) || subtotal < 0 ||
        typeof estrategia !== "function") throw Error("Contrato");
    return estrategia(subtotal);
}
module.exports = { cobrar, conDescuento };

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\04-patrones-diseno\strategy\tarifas\main.js
 * Nota: tarifas.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   100 90
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
