// Tema:
// La creación de una instancia establece primero valores predeterminados. Se ejecutan
// constructores e inicializadores de la jerarquía de superclase a subclase.
// Compilar: javac --release 21 -encoding UTF-8 -d . PuntoInicial.java PuntoExtendido.java CrearInstancia.java
// Ejecutar: java CrearInstancia
// Comandos desde la carpeta de la unidad.

public class CrearInstancia {
    public static void main(String[] args) {
        PuntoExtendido p = new PuntoExtendido();
        System.out.println(p.x + " " + p.y + " " + p.color);
    }
}

// Salida esperada:
// 2 3 8
