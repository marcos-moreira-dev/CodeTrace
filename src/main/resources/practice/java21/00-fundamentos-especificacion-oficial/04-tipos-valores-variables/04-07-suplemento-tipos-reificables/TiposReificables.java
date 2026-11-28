// Tema:
// Un tipo reificable conserva suficiente información en ejecución para comprobarse. List<?>
// admite instanceof; List<String> no es reificable en esta comprobación de un Object.
// Compilar: javac --release 21 -encoding UTF-8 -d . TiposReificables.java
// Ejecutar: java TiposReificables
// Comandos desde la carpeta de la unidad.

public class TiposReificables {
    public static void main(String[] args) {
        Object dato = java.util.List.of("sol", "mar");
        if (dato instanceof java.util.List<?> lista) {
            System.out.println(lista.size());
        }
    }
}

// Salida esperada:
// 2
