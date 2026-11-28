// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.11.2; ejemplo 15.11.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . NivelI.java Nivel1.java Nivel2.java SuperCampos.java
// Ejecutar: java SuperCampos
// Comandos desde la carpeta de la unidad.

class SuperCampos extends Nivel2 {
    int x = 3;
    void mostrar() {
        System.out.println(x);
        System.out.println(super.x);
        System.out.println(((Nivel2)this).x);
        System.out.println(((Nivel1)this).x);
        System.out.println(((NivelI)this).x);
    }
    public static void main(String[] args) { new SuperCampos().mostrar(); }
}

// Salida esperada:
// 3
// 2
// 2
// 1
// 0
