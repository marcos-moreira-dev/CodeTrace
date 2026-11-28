// Tema:
// El acceso mediante punto exige que el nombre de la izquierda permita seleccionar el miembro
// solicitado. Un int no es una referencia y un campo no se invoca como método.
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
