function total(precio, cantidad) {
  return precio * cantidad;
}

const aplicarDescuento = (valor) => valor * 0.9;
console.log(aplicarDescuento(total(12, 3)));
