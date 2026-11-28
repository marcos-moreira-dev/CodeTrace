// Tema:
// Dos campos de cuatro bits pueden ocupar un mismo byte sin solaparse. Las máscaras limitan
// cada valor al ancho reservado.
// Modalidad: ejecución Java real.
// Compilar: javac --release 21 -encoding UTF-8 EmpaquetarCampos.java
// Ejecutar: java EmpaquetarCampos

public class EmpaquetarCampos {
    public static void main(String[] args) {
        int alto = 9, bajo = 6;
        int dato = ((alto & 15) << 4) | (bajo & 15);
        System.out.println(Integer.toHexString(dato));
        System.out.println((dato >>> 4) & 15);
        System.out.println(dato & 15);
    }
}

// Salida esperada:
// 96
// 9
// 6
