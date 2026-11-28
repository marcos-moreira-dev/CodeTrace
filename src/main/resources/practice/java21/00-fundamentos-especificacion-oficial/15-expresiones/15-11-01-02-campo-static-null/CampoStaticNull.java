// Tema:
// Un acceso a un campo static mediante una expresión evalúa esa expresión, pero no necesita
// una instancia. Que el resultado de la expresión sea null no provoca por sí solo
// NullPointerException.
// Compilar: javac --release 21 -encoding UTF-8 -d . CampoStaticNull.java
// Ejecutar: java CampoStaticNull
// Comandos desde la carpeta de la unidad.

public class CampoStaticNull {
    static int valor = 7;
    static CampoStaticNull receptor() {
        System.out.println("receptor evaluado");
        return null;
    }
    public static void main(String[] args) { System.out.println(receptor().valor); }
}

// Salida esperada:
// receptor evaluado
// 7
