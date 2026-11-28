// Tema:
// Una llamada a un método sobrescrito durante la construcción puede alcanzar la implementación
// de la subclase antes de que sus campos hayan recibido sus inicializadores.
// Compilar: javac --release 21 -encoding UTF-8 -d . ConstructorBase.java DespachoConstructor.java
// Ejecutar: java DespachoConstructor
// Comandos desde la carpeta de la unidad.

public class DespachoConstructor extends ConstructorBase {
    int dato = 8;
    @Override void mostrar() { System.out.println(dato); }
    public static void main(String[] args) {
        DespachoConstructor d = new DespachoConstructor();
        d.mostrar();
    }
}

// Salida esperada:
// 0
// 8
