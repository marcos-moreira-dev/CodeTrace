// Tema: Exhaustividad de una unión
// Tras cubrir todos los casos, el valor restante es never. Añadir una dirección sin manejar provocaría un diagnóstico.
// Desde la carpeta de este ejercicio:
// Compilar: tsc --strict --target ES2022 03.ts
// Ejecutar: node 03.js
// Salida esperada:
// arriba
// abajo

type Direction = 'up' | 'down';
function move(direction: Direction): void {
    switch (direction) {
        case 'up': console.log('arriba'); break;
        case 'down': console.log('abajo'); break;
        default: { const check: never = direction; throw new Error(String(check)); }
    }
}
move('up');
move('down');
