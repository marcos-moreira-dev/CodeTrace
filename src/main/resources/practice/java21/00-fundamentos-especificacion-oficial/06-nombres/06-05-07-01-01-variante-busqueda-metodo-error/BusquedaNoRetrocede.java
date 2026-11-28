// Tema:
// La búsqueda del nombre simple elige el tipo interior que contiene un método con ese
// nombre. Si su firma no acepta el argumento, no se prueba la sobrecarga del objeto
// exterior.
// Compilar: javac --release 21 -encoding UTF-8 -d . BusquedaNoRetrocede.java
// ERROR INTENCIONAL de compilación. No ejecutar.

class BusquedaNoRetrocede {
    void guardar(int n) {}
    static class Base { void guardar(String s) {} }
    void probar() {
        new Base() {
            { guardar(3); }
        };
    }
}

// Salida esperada:
// Error: int cannot be converted to String
