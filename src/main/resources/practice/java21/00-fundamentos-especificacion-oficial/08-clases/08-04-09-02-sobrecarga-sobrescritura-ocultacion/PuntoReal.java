// Tema:
// La sobrecarga se resuelve al compilar; la sobrescritura de instancia se despacha en
// ejecución. Los campos se seleccionan por el tipo estático y pueden quedar ocultos.
// Compilar: javac --release 21 -encoding UTF-8 -d . Punto.java PuntoReal.java
// Ejecutar: java PuntoReal
// Comandos desde la carpeta de la unidad.

class PuntoReal extends Punto {
    float x, y;
    @Override void mover(int dx, int dy) { mover((float)dx, (float)dy); }
    void mover(float dx, float dy) { x += dx; y += dy; }
    public static void main(String[] args) {
        PuntoReal p = new PuntoReal();
        Punto base = p;
        base.mover(2, 3);
        p.mover(0.5f, 0.5f);
        System.out.println(p.x + " " + p.y);
        System.out.println(base.x + " " + base.y);
    }
}

// Salida esperada:
// 2.5 3.5
// 0 0
