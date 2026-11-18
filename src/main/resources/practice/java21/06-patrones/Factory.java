public final class Factory {
    interface Mensaje {
        String enviar();
    }

    record Correo(String destino) implements Mensaje {
        public String enviar() { return "Correo a " + destino; }
    }

    public static Mensaje crear(String destino) {
        return new Correo(destino);
    }
}
