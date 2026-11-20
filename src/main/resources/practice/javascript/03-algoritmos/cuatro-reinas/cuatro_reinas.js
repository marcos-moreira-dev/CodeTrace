// CodeTrace | Referencia: JavaScript ES2022 / Node.js 24
// Compilar/Comprobar: node --check 03-algoritmos/cuatro-reinas/cuatro_reinas.js
// Ejecutar: node 03-algoritmos/cuatro-reinas/cuatro_reinas.js

/**
 * Cliente de backtracking para cuatro reinas.
 * Cada fila elige una columna que no esté atacada. Una decisión
 * inválida se poda; al volver de la recursión se deshace el cambio.
 * La búsqueda es combinatoria en el peor caso, no polinómica.
 */
function resolver(n) {
    const columnas = [], soluciones = [];
    function valido(fila, columna) {
        return columnas.every((c, f) =>
            c !== columna && Math.abs(c - columna) !== Math.abs(f - fila));
    }
    function buscar(fila) {
        if (fila === n) { soluciones.push([...columnas]); return; }
        for (let col = 0; col < n; col++) {
            if (!valido(fila, col)) continue;
            columnas.push(col);
            buscar(fila + 1);
            columnas.pop();
        }
    }
    buscar(0);
    return soluciones;
}
console.log(resolver(4));
