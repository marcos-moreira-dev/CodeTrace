// Tema:
// Los límites de parámetros genéricos pueden referirse entre sí. El compilador comprueba que
// cada tipo cumple el contrato que lo relaciona con el otro.
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
