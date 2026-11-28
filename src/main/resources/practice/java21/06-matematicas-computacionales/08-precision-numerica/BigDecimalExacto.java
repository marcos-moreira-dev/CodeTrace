// Tema:
// BigDecimal construido desde texto conserva el decimal de entrada.
// Es apropiado para operaciones decimales exactas como una suma monetaria.
// Modalidad: algoritmo ejecutable.
// Compilar: javac --release 21 -encoding UTF-8 BigDecimalExacto.java
// Ejecutar: java BigDecimalExacto

import java.math.BigDecimal;

public class BigDecimalExacto {
    public static void main(String[] args) {
        BigDecimal a = new BigDecimal("0.10");
        BigDecimal b = new BigDecimal("0.20");
        System.out.println(a.add(b).toPlainString());
    }
}

// Salida esperada:
// 0.30
