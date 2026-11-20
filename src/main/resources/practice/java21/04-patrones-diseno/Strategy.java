// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-java21" Strategy
public final class Strategy {
    interface Descuento {
        double aplicar(double total);
    }

    static double cobrar(double total, Descuento estrategia) {
        return estrategia.aplicar(total);
    }

    public static void main(String[] args) {
        Descuento clienteFiel = total -> total * 0.90;
        System.out.println(cobrar(50, clienteFiel));
    }
}
