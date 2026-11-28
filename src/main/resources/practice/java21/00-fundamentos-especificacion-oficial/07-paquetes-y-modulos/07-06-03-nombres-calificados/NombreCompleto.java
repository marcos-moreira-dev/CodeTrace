// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §7.6; ejemplo 7.6-3
// Compilar: javac --release 21 -encoding UTF-8 -d . Punto.java vista/Punto.java NombreCompleto.java
// Ejecutar: java NombreCompleto
// Comandos desde la carpeta de la unidad.

class NombreCompleto {
    public static void main(String[] args) {
        System.out.println(Punto.class.getName());
        System.out.println(vista.Punto.class.getName());
    }
}

// Salida esperada:
// Punto
// vista.Punto
