// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Comprobar: node --check ".\01-fundamentos\promesas-asincronas\promesas_asincronas.js"
// Ejecutar: node ".\01-fundamentos\promesas-asincronas\promesas_asincronas.js"

/**
 * Cliente de Promise.all y errores asíncronos.
 *
 * Las promesas modelan un resultado pendiente. Promise.all espera
 * todos los resultados, o rechaza si falla alguno. El método async
 * expresa secuencias lógicas sin bloquear el event loop.
 * El ejemplo usa tareas locales deterministas.
 */
async function consultar(id) {
    if (id < 0) throw new Error("ID negativo");
    return { id, estado: "listo" };
}
async function main() {
    try {
        const estados = await Promise.all([consultar(1), consultar(2)]);
        console.log(estados);
    } catch (error) {
        console.error(error.message);
    }
}
void main();
