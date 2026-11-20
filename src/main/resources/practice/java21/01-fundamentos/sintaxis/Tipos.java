// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-java21" Tipos
public class Tipos {
    public static void main(String[] args) {
        int unidades = 5;
        double precio = 12.50;
        boolean disponible = true;
        String producto = "Cuaderno";
        double total = unidades * precio;
        System.out.println(producto + ": " + total);
        System.out.println(disponible);
    }
}
