// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §3.10.6; ejemplo 3.10.6-4
// Compilar: javac --release 21 -encoding UTF-8 -d . BloqueComoCadena.java
// Ejecutar: java BloqueComoCadena
// Comandos desde la carpeta de la unidad.

public class BloqueComoCadena {
    public static void main(String[] args) {
        String texto = """
                planeta""";
        System.out.println("Sol y " + texto);
        System.out.println(texto.substring(3));
        System.out.println(texto == "planeta");
    }
}

// Salida esperada:
// Sol y planeta
// neta
// true
