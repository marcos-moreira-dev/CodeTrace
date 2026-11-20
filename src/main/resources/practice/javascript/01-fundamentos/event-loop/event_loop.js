// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Compilar/Comprobar: node --check 01-fundamentos/event-loop/event_loop.js
// Ejecutar: node 01-fundamentos/event-loop/event_loop.js

/**
 * Cliente de microtareas y timers.
 * Una Promise resuelta agenda una continuación como microtarea.
 * Un timer se ejecuta posteriormente, no en un tiempo exacto.
 * Esta diferencia importa en interfaces y servidores asíncronos.
 */
console.log("1. Síncrono");
setTimeout(() => console.log("3. Timer"), 0);
Promise.resolve().then(() => console.log("2. Microtarea"));
