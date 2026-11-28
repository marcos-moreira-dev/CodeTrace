// Tema:
// Un campo static pertenece a la clase y se comparte entre todas sus instancias. Un campo de
// instancia tiene una copia independiente en cada objeto.
// Compilar: javac --release 21 -encoding UTF-8 -d . CamposCompartidos.java
// Ejecutar: java CamposCompartidos
// Comandos desde la carpeta de la unidad.

public class CamposCompartidos {
    static int total;
    int individual;
    CamposCompartidos() { total++; individual++; }
    public static void main(String[] args) {
        CamposCompartidos a = new CamposCompartidos();
        CamposCompartidos b = new CamposCompartidos();
        System.out.println(total);
        System.out.println(a.individual + " " + b.individual);
    }
}

// Salida esperada:
// 2
// 1 1
