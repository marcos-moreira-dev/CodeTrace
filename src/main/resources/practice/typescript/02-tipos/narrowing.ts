type Resultado =
  | { ok: true; valor: number }
  | { ok: false; error: string };

function mostrar(resultado: Resultado): string {
  if (resultado.ok) return String(resultado.valor);
  return resultado.error;
}
