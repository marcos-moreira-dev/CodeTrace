type Receptor = (mensaje: string) => void;

function notificar(texto: string, receptor: Receptor): void {
  receptor(texto);
}

function mostrar(texto: string): void {
  console.log(texto);
}

notificar("Hola", mostrar);
notificar("Lista", (mensaje) => console.log(mensaje));

export { notificar };
