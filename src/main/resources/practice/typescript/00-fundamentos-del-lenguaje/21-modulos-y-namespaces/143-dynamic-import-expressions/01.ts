// Tema: dynamic import expressions.
// import() carga un módulo de manera asíncrona. La ejecución requiere el HTML de esta carpeta en un navegador.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --noEmit --strict --target ES2022 --module ESNext --moduleResolution Bundler 01.ts widget.ts
// Para emitir: tsc --strict --target ES2022 --module ESNext --moduleResolution Bundler 01.ts widget.ts
// Servir esta carpeta por HTTP local y abrir index.html. El texto mostrado será Widget listo.
// Salida esperada: comprobación de tipos; ejecutar solo en el entorno indicado, no en Node por defecto.

async function renderWidget(): Promise<void> {
    const container = document.getElementById('widget');
    if (container !== null) {
        const widget = await import('./widget.js');
        widget.render(container);
    }
}
void renderWidget();
