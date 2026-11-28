// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.5.6.2; ejemplo 6.5.6.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Punto.java ExpresionCalificada.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class ExpresionCalificada {
    public static void main(String[] args) {
        int n = 0;
        n.x++;
        Punto p = new Punto();
        p.total();
    }
}

// Salida esperada:
// Error: int cannot be dereferenced
