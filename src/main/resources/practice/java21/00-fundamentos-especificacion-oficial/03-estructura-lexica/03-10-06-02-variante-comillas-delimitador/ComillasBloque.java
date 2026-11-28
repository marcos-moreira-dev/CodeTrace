// Tema:
// Dentro de un bloque de texto se pueden escribir comillas dobles ordinarias sin escape. Si
// la última comilla de contenido queda junto al delimitador de cierre, se escapa para
// evitar que forme parte de las tres comillas delimitadoras.
// Compilar: javac --release 21 -encoding UTF-8 -d . ComillasBloque.java
// Ejecutar: java ComillasBloque

public class ComillasBloque {
    public static void main(String[] args) {
        String mensaje = """
                Ella dijo: "hola"
                """;
        String sinSalto = """
                Ella dijo: "hola\"""";
        System.out.print(mensaje);
        System.out.println(mensaje.stripTrailing().equals(sinSalto));
    }
}

// Salida esperada:
// Ella dijo: "hola"
// true
