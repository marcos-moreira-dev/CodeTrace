// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check main.js
// Ejecutar: node main.js (desde copia de esta carpeta)
// Módulos locales CommonJS sin dependencias de npm.

class Legado { fahrenheit() { return 77; } }
/** Adapter traduce el proveedor externo al contrato leer() del dominio. */
class Adaptador {
    constructor(legado) { this.legado = legado; }
    leer() { return (this.legado.fahrenheit() - 32) * 5 / 9; }
}
module.exports = { Legado, Adaptador };

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\04-patrones-diseno\adapter\unidades\main.js
 * Nota: sensores.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   Celsius: 25
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
