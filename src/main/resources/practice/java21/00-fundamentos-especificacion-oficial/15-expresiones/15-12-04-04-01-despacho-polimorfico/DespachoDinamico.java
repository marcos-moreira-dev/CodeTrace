// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.12.4.4; ejemplo 15.12.4.4-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Posicion.java PosicionColor.java DespachoDinamico.java
// Ejecutar: java DespachoDinamico
// Comandos desde la carpeta de la unidad.

class DespachoDinamico {
    public static void main(String[] args) {
        PosicionColor p = new PosicionColor();
        p.mover(12);
        System.out.println(p.x + " " + p.color);
    }
}

// Salida esperada:
// color
// posición
// 0 0
