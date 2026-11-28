// Tema:
// Un cast de referencia comprueba que el objeto real satisface la clase o interfaz destino.
// Las referencias convertidas siguen apuntando al mismo objeto; una conversión incompatible
// lanza ClassCastException.
// Compilar: javac --release 21 -encoding UTF-8 -d . PuntoPlano.java Coloreable.java PuntoColor.java CastReferencias.java
// Ejecutar: java CastReferencias
// Comandos desde la carpeta de la unidad.

public class CastReferencias {
    public static void main(String[] args) {
        PuntoPlano base = new PuntoColor();
        PuntoColor color = (PuntoColor) base;
        Coloreable interfaz = (Coloreable) base;
        interfaz.color(5);
        System.out.println(color.tono);
        try { color = (PuntoColor) new PuntoPlano(); }
        catch (ClassCastException e) { System.out.println("cast inválido"); }
    }
}

// Salida esperada:
// 5
// cast inválido
