// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check ".\01-fundamentos\map-set-referencias\map_set_referencias.js"
// Ejecutar: node ".\01-fundamentos\map-set-referencias\map_set_referencias.js"

/**
 * Cliente de Map, Set y semántica de referencias.
 *
 * Map guarda asociaciones clave-valor y Set mantiene unicidad.
 * Dos objetos con los mismos campos no son la misma identidad:
 * las claves objeto se comparan por referencia, no por contenido.
 * Se contrasta con claves primitivas, que sí comparan por valor.
 */
const historial = new Map();
const objeto = { codigo: "A" };
historial.set(objeto, 42);
console.log(historial.get(objeto));
console.log(historial.get({ codigo: "A" })); // undefined
const claves = new Set(["A", "B", "A"]);
console.log([...claves]);

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   node .\01-fundamentos\map-set-referencias\map_set_referencias.js
 * Salida esperada:
 *   42
 *   undefined
 *   [ 'A', 'B' ]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
