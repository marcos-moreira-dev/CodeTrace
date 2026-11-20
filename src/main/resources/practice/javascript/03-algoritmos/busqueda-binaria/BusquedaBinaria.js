// CodeTrace | Referencia: JavaScript ES2022, Node.js 24.
// Desde PowerShell en una COPIA del paquete:
// Comprobar sintaxis: node --check '.\03-algoritmos\BusquedaBinaria.js'
// Ejecutar: node '.\03-algoritmos\BusquedaBinaria.js'

function buscar(datos, objetivo) {
  let izquierda = 0, derecha = datos.length - 1;
  while (izquierda <= derecha) {
    const medio = izquierda + Math.floor((derecha - izquierda) / 2);
    if (datos[medio] === objetivo) return medio;
    if (datos[medio] < objetivo) izquierda = medio + 1;
    else derecha = medio - 1;
  }
  return -1;
}
console.log(buscar([2, 4, 8, 12, 16], 12));
