export function mcd(a: number, b: number): number {
  if (!Number.isSafeInteger(a) || !Number.isSafeInteger(b) || a < 0 || b < 0)
    throw new Error("Usa enteros no negativos y seguros");

  while (b !== 0) {
    const resto = a % b;
    a = b;
    b = resto;
  }
  return a;
}
