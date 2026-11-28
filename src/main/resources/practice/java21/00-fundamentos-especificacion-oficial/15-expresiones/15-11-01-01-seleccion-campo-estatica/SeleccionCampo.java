// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.11.1; ejemplo 15.11.1-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Base.java Derivada.java SeleccionCampo.java
// Ejecutar: java SeleccionCampo
// Comandos desde la carpeta de la unidad.

class SeleccionCampo {
    public static void main(String[] args) {
        Derivada d = new Derivada();
        Base b = new Base();
        System.out.println(d.dato);
        System.out.println(b.dato);
        b = d;
        System.out.println(b.dato);
    }
}

// Salida esperada:
// 7
// 3
// 3
