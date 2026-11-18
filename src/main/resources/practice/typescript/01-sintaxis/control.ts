function sumarPares(limite: number): number {
  let total = 0;
  for (let i = 0; i <= limite; i++) {
    if (i % 2 === 0) total += i;
  }
  return total;
}

console.log(sumarPares(10));
