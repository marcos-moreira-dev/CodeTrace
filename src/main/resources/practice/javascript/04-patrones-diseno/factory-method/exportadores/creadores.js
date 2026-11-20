// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check main.js
// Ejecutar: node main.js (desde copia de esta carpeta)
// Módulos locales CommonJS sin dependencias de npm.

/** Un creador define preparar y delega la fábrica a una subclase. */
class Creador {
    crear() { throw Error("Implementar en subclase"); }
    preparar(texto) { return this.crear().exportar(texto); }
}
class ExportadorTexto {
    exportar(texto) { return "TXT: " + texto; }
}
class CreadorTexto extends Creador {
    crear() { return new ExportadorTexto(); }
}
module.exports = { Creador, CreadorTexto };

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\04-patrones-diseno\factory-method\exportadores\main.js
 * Nota: creadores.js es un componente; la salida corresponde al cliente main.js.
 * Salida esperada:
 *   TXT: Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
