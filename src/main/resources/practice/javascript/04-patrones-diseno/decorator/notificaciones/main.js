// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check main.js
// Ejecutar: node main.js (desde copia de esta carpeta)
// Módulos locales CommonJS sin dependencias de npm.

const { Consola, Prefijo } = require("./notificadores");
/**
 * Cliente Decorator: añadir responsabilidades por composición.
 *
 * A diferencia de Adapter, se conserva exactamente el contrato
 * enviar(). Las capas son combinables sin crear una clase para
 * cada posible combinación de prefijos.
 */
function main() {
    const emisor = new Prefijo(new Consola(), "[INFO] ");
    emisor.enviar("Servicio activo");
}
if (require.main === module) main();

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\04-patrones-diseno\decorator\notificaciones\main.js
 * Salida esperada:
 *   [INFO] Servicio activo
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
