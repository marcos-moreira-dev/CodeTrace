// Tema:
// Una llamada static puede evaluar una expresión receptora sin necesitar el objeto resultante.
// El método se selecciona por el tipo y no falla solo porque la expresión produzca null.
// Compilar: javac --release 21 -encoding UTF-8 -d . ReceptorStatic.java
// Ejecutar: java ReceptorStatic
// Comandos desde la carpeta de la unidad.

class ReceptorStatic {
    static void mostrar() { System.out.println("método"); }
    static ReceptorStatic receptor() {
        System.out.println("receptor");
        return null;
    }
    public static void main(String[] args) { receptor().mostrar(); }
}

// Salida esperada:
// receptor
// método
