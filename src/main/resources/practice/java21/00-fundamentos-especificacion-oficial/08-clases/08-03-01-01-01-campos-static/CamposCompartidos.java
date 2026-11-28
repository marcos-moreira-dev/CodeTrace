// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.3.1.1; ejemplo 8.3.1.1-1
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
