// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §9.4.2; ejemplo 9.4.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Movil.java MovilPreciso.java SobrecargaInterfaz.java
// Ejecutar: java SobrecargaInterfaz
// Comandos desde la carpeta de la unidad.

public class SobrecargaInterfaz implements MovilPreciso {
    public void mover(int n) { System.out.println("int"); }
    public void mover(float n) { System.out.println("float"); }
    public void mover(double n) { System.out.println("double"); }
    public static void main(String[] args) {
        MovilPreciso m = new SobrecargaInterfaz();
        m.mover(1); m.mover(1f); m.mover(1d);
    }
}

// Salida esperada:
// int
// float
// double
