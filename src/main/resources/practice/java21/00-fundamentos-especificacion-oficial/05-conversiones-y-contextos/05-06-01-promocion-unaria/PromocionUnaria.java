// Tema:
// Los operandos byte, short y char se promocionan a int en operaciones unarias como negación y
// complemento. Los desplazamientos también promocionan cada operando por separado.
// Compilar: javac --release 21 -encoding UTF-8 -d . PromocionUnaria.java
// Ejecutar: java PromocionUnaria
// Comandos desde la carpeta de la unidad.

public class PromocionUnaria {
    public static void main(String[] args) {
        byte valor = 3;
        int negativo = -valor;
        int complemento = ~valor;
        int desplazado = valor << 2;
        System.out.println(negativo);
        System.out.println(complemento);
        System.out.println(desplazado);
    }
}

// Salida esperada:
// -3
// -4
// 12
