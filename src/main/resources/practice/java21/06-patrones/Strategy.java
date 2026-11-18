// CodeTrace | Referencia: Java 21 LTS (Temurin JDK 21).
// Desde PowerShell en una COPIA de este paquete:
// Compilar: javac -encoding UTF-8 -d "$env:TEMP\codetrace-java" '.\06-patrones\Strategy.java'
// Ejecutar: java -cp "$env:TEMP\codetrace-java" Strategy

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
