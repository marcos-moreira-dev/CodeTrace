// Tema:
// El constructor canónico explícito recibe los componentes en el mismo orden y tipo que la
// cabecera. Debe inicializar todos sus campos.
// Compilar: javac --release 21 -encoding UTF-8 -d . Intervalo.java ConstructorCanonico.java
// Ejecutar: java ConstructorCanonico
// Comandos desde la carpeta de la unidad.

record Intervalo(int inicio, int fin) {
    Intervalo(int inicio, int fin) {
        if (inicio > fin) throw new IllegalArgumentException("orden");
        this.inicio = inicio;
        this.fin = fin;
    }
}

// Salida esperada:
// 5
// orden
