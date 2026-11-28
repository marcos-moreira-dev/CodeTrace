// Tema:
// Los límites de parámetros genéricos pueden referirse entre sí. El compilador comprueba que
// cada tipo cumple el contrato que lo relaciona con el otro.
// Compilar: javac --release 21 -encoding UTF-8 -d . Convertible.java Puente.java Metros.java Centimetros.java LimitesReciprocos.java
// Ejecutar: java LimitesReciprocos
// Comandos desde la carpeta de la unidad.

class Puente<A extends Convertible<B>, B extends Convertible<A>> {
    A dato;
    void recibir(B origen) { dato = origen.convertir(); }
    B devolver() { return dato.convertir(); }
}

// Salida esperada:
// 3
// 300
