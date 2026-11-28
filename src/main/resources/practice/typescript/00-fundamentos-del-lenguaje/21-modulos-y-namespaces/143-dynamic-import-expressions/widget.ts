// Tema: Módulo cargado dinámicamente en el navegador.
// Compilar: tsc --strict --target ES2022 --module ESNext --moduleResolution Bundler 01.ts widget.ts
// Abrir index.html mediante un servidor HTTP local.
export function render(container: HTMLElement):void { container.textContent="Widget listo"; }
