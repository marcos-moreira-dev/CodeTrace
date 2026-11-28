// Tema:
// La promoción numérica binaria selecciona un tipo común: double, float, long o int, en ese
// orden de prioridad. La operación se realiza con los valores ya convertidos.
// Compilar: javac --release 21 -encoding UTF-8 -d . PromocionBinaria.java
// Ejecutar: java PromocionBinaria
// Comandos desde la carpeta de la unidad.

public class PromocionBinaria {
    public static void main(String[] args) {
        int entero = 5;
        long largo = 2L;
        float flotante = 0.5f;
        double doble = 0.25;
        System.out.println(entero + largo);
        System.out.println(largo + flotante);
        System.out.println(flotante + doble);
    }
}

// Salida esperada:
// 7
// 2.5
// 0.75
