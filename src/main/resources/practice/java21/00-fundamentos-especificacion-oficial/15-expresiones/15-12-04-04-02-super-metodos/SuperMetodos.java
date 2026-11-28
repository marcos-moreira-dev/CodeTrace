// Tema:
// super permite invocar la implementación de la superclase directa. Un cast a un supertipo no
// elimina el despacho dinámico de una invocación normal.
// Compilar: javac --release 21 -encoding UTF-8 -d . Nivel1.java Nivel2.java SuperMetodos.java
// Ejecutar: java SuperMetodos
// Comandos desde la carpeta de la unidad.

class SuperMetodos extends Nivel2 {
    String texto() { return "3"; }
    void mostrar() {
        System.out.println(texto());
        System.out.println(super.texto());
        System.out.println(((Nivel2)this).texto());
        System.out.println(((Nivel1)this).texto());
    }
    public static void main(String[] args) { new SuperMetodos().mostrar(); }
}

// Salida esperada:
// 3
// 2
// 3
// 3
