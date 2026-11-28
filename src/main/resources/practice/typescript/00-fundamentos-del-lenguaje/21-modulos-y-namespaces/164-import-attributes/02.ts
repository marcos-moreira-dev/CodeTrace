// Tema: import attributes.
// La importación dinámica retorna una promesa y entrega el JSON mediante la exportación default.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 --module ESNext --moduleResolution Bundler --resolveJsonModule 02.ts
// Ejecutar: node 02.js
// Salida esperada:
// demo

async function leer(): Promise<void> {
    const config = await import('./config.json', { with: { type: 'json' } });
    console.log(config.default.nombre);
}
void leer();
