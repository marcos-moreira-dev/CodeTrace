// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §12.5; ejemplo 12.5-1
// Compilar: javac --release 21 -encoding UTF-8 -d . PuntoInicial.java PuntoExtendido.java CrearInstancia.java
// Ejecutar: java CrearInstancia
// Comandos desde la carpeta de la unidad.

class PuntoInicial {
    int x, y;
    PuntoInicial() { x = 2; y = 3; }
}

// Salida esperada:
// 2 3 8
