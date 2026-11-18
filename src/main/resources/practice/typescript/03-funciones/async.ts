async function consultar(): Promise<number> {
  return 21;
}

async function iniciar(): Promise<void> {
  const resultado = await consultar();
  console.log(resultado);
}

void iniciar();
