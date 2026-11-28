// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.1; ejemplo 6.1-3
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
