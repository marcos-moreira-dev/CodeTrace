// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §12.5; ejemplo 12.5-2
// Compilar: javac --release 21 -encoding UTF-8 -d . ConstructorBase.java DespachoConstructor.java
// Ejecutar: java DespachoConstructor
// Comandos desde la carpeta de la unidad.

class ConstructorBase {
    ConstructorBase() { mostrar(); }
    void mostrar() { System.out.println("base"); }
}

// Salida esperada:
// 0
// 8
