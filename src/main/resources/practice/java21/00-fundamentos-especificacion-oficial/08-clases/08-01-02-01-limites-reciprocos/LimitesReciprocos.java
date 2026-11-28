// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.1.2; ejemplo 8.1.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Convertible.java Puente.java Metros.java Centimetros.java LimitesReciprocos.java
// Ejecutar: java LimitesReciprocos
// Comandos desde la carpeta de la unidad.

public class LimitesReciprocos {
    public static void main(String[] args) {
        Puente<Metros, Centimetros> puente = new Puente<>();
        puente.recibir(new Centimetros(300));
        System.out.println(puente.dato.cantidad());
        System.out.println(puente.devolver().cantidad());
    }
}

// Salida esperada:
// 3
// 300
