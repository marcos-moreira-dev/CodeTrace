// Tema:
// import() solicita un módulo dinámicamente y devuelve una promesa con sus exportaciones.

async function renderWidget() {
    const container = document.getElementById('widget');
    if (container !== null) {
        const widget = await import('./widget'); // Dynamic import
        widget.render(container);
    }
}

renderWidget();
