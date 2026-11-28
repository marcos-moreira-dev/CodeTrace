// Tema:
// La creación pasa por el constructor compacto; el valor normalizado se almacena en el
// componente sin una asignación explícita a this.texto.
// Compilar: javac --release 21 -encoding UTF-8 -d . Nombre.java ConstructorCompacto.java
// Ejecutar: java ConstructorCompacto
// Comandos desde la carpeta de la unidad.

public class ConstructorCompacto {
    public static void main(String[] args) {
        System.out.println("[" + new Nombre("  Ana  ").texto() + "]");
        try { new Nombre("   "); }
        catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
    }
}

// Salida esperada:
// [Ana]
// vacío
