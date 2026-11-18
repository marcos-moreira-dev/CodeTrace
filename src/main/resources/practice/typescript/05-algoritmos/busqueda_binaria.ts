export function busquedaBinaria(datos: readonly number[], x: number): number {
  let izquierda = 0;
  let derecha = datos.length - 1;
  while (izquierda <= derecha) {
    const medio = izquierda + Math.floor((derecha - izquierda) / 2);
    if (datos[medio] === x) return medio;
    if (datos[medio] < x) izquierda = medio + 1;
    else derecha = medio - 1;
  }
  return -1;
}
