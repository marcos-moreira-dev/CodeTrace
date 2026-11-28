// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §4.12.5; ejemplo 4.12.5-1
// Compilar: javac --release 21 -encoding UTF-8 -d . ValoresIniciales.java Registro.java
// Ejecutar: java ValoresIniciales
// Tema JLS:
// No hay inicializadores explícitos. Java asigna cero a total y numero, false a activo y
// null a siguiente. El campo static se prepara por clase; los demás se inicializan por
// objeto.
// Comandos desde la carpeta de la unidad.

class Registro {
    static int total;
    int numero;
    boolean activo;
    Registro siguiente;
}

// Salida esperada:
// 0
// 0
// false
// null
