// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.12.4.1; ejemplo 15.12.4.1-1
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
