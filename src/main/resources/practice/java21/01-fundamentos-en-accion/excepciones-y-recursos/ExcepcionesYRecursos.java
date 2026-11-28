// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 ExcepcionesYRecursos.java
// Ejecutar: java ExcepcionesYRecursos

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Objects;

/**
 * Cliente de checked exceptions y try-with-resources.
 *
 * <p>Una excepción comprobada expresa un fallo que el cliente debe
 * capturar o propagar. try-with-resources cierra un AutoCloseable aunque
 * se lance una excepción durante su uso. Aquí StringReader evita archivos
 * externos y permite estudiar el contrato sin efectos secundarios.
 */
public class ExcepcionesYRecursos {
    static int leerNumero(String texto) throws java.io.IOException {
        Objects.requireNonNull(texto);
        try (BufferedReader entrada = new BufferedReader(new StringReader(texto))) {
            return Integer.parseInt(entrada.readLine());
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("Número: " + leerNumero("25"));
        } catch (java.io.IOException | NumberFormatException ex) {
            System.out.println("Entrada inválida: " + ex.getMessage());
        }
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 ExcepcionesYRecursos.java
 *   java ExcepcionesYRecursos
 * Salida esperada:
 *   Número: 25
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
