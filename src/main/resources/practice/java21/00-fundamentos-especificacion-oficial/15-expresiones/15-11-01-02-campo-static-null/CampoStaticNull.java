// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §15.11.1; ejemplo 15.11.1-2
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
