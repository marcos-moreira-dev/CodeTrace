interface Notificacion {
  enviar(mensaje: string): string;
}

class Correo implements Notificacion {
  enviar(mensaje: string): string {
    return "Correo: " + mensaje;
  }
}

function crearNotificacion(): Notificacion {
  return new Correo();
}

console.log(crearNotificacion().enviar("Listo"));
