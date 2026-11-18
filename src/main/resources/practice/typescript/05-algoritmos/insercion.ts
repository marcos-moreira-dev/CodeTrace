export function insercion(valores: number[]): void {
  for (let i = 1; i < valores.length; i++) {
    const actual = valores[i];
    let j = i - 1;
    while (j >= 0 && valores[j] > actual) {
      valores[j + 1] = valores[j];
      j--;
    }
    valores[j + 1] = actual;
  }
}
