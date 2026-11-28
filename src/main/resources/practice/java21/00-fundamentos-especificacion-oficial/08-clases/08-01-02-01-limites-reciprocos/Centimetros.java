// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.1.2; ejemplo 8.1.2-1
// Compilar: javac --release 21 -encoding UTF-8 -d . Convertible.java Puente.java Metros.java Centimetros.java LimitesReciprocos.java
// Ejecutar: java LimitesReciprocos
// Comandos desde la carpeta de la unidad.

record Centimetros(int cantidad) implements Convertible<Metros> {
    public Metros convertir() { return new Metros(cantidad / 100); }
}

// Salida esperada:
// 3
// 300
