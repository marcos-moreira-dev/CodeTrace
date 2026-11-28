// Tema:
// Los campos y componentes de arreglo reciben valores predeterminados: cero, false o null
// según su tipo. Las variables locales deben asignarse antes de leerse.
// Compilar: javac --release 21 -encoding UTF-8 -d . ValoresIniciales.java Registro.java
// Ejecutar: java ValoresIniciales
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
