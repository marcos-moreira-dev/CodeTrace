// Tema:
// Un campo static declarado en una subclase puede ocultar un campo heredado del mismo nombre.
// La selección depende del tipo usado, no del despacho dinámico.
// Compilar: javac --release 21 -encoding UTF-8 -d . BaseEstatica.java OcultarEstatico.java
// Ejecutar: java OcultarEstatico
// Comandos desde la carpeta de la unidad.

public class OcultarEstatico extends BaseEstatica {
    static double dato = 6.5;
    void mostrar() { System.out.println(dato + " " + super.dato); }
    public static void main(String[] args) { new OcultarEstatico().mostrar(); }
}

// Salida esperada:
// 6.5 3
