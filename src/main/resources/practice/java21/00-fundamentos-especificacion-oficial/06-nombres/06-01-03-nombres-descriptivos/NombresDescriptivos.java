// Tema:
// Los nombres de clases suelen usar sustantivos descriptivos en UpperCamelCase; los de métodos
// y variables, lowerCamelCase. Son convenciones de legibilidad, no restricciones generales de
// sintaxis.
// Compilar: javac --release 21 -encoding UTF-8 -d . EntradaTexto.java NombresDescriptivos.java
// Ejecutar: java NombresDescriptivos
// Comandos desde la carpeta de la unidad.

class NombresDescriptivos {
    public static void main(String[] args) {
        // Sustantivo descriptivo en UpperCamelCase; no abreviatura arbitraria.
        EntradaTexto entrada = new EntradaTexto();
        System.out.println(entrada.leer());
    }
}

// Salida esperada:
// texto
