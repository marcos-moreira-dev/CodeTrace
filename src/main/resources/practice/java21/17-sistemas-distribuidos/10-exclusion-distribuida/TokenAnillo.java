// Tema:
// Token en anillo: solamente el poseedor puede entrar a una sección exclusiva; debe salir antes de transferir el token. El modelo supone un único token, participantes fijos y entrega fiable; perder o duplicar el token rompe el supuesto.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 TokenAnillo.java MainTokenAnillo.java
// Ejecutar: java MainTokenAnillo

public class TokenAnillo {
    private final int nodos;
    private int poseedor;
    private boolean ocupado;
    public TokenAnillo(int nodos) {
        if (nodos < 1) throw new IllegalArgumentException("Nodos");
        this.nodos = nodos;
    }
    public boolean entrar(int id) {
        if (id != poseedor || ocupado) return false;
        ocupado = true;
        return true;
    }
    public void salir(int id) {
        if (id != poseedor || !ocupado) throw new IllegalStateException("Sin acceso");
        ocupado = false;
    }
    public void pasar(int id) {
        if (id != poseedor || ocupado) throw new IllegalStateException("Token ocupado");
        poseedor = (poseedor + 1) % nodos;
    }
}

// Salida esperada:
// Nodo 1 antes: false
// Nodo 0: true
// Nodo 1: true
// Nodo 2: true
