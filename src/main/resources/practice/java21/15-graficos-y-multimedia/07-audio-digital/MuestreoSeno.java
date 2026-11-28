// Tema:
// Señal seno muestreada a intervalos uniformes. Ejemplo de 2 Hz con 8 muestras por segundo: cuatro muestras por ciclo; redondeamos solo al imprimir.
// Modalidad: ejecución real con datos locales.
// Compilar: javac --release 21 -encoding UTF-8 MuestreoSeno.java
// Ejecutar: java MuestreoSeno

public class MuestreoSeno {
    public static void main(String[] args) throws Exception {
        int frecuencia = 2, tasa = 8;
        for (int n = 0; n < 4; n++) {
            double t = (double) n / tasa;
            double amplitud = Math.sin(2 * Math.PI * frecuencia * t);
            System.out.println(Math.round(amplitud));
        }
    }
}

// Salida esperada:
// 0
// 1
// 0
// -1
