// Tema:
// Token en anillo: solamente el poseedor puede entrar a una sección exclusiva; debe salir antes de transferir el token. El modelo supone un único token, participantes fijos y entrega fiable; perder o duplicar el token rompe el supuesto.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 TokenAnillo.java MainTokenAnillo.java
// Ejecutar: java MainTokenAnillo

public class MainTokenAnillo {
    public static void main(String[] args) {
        TokenAnillo anillo = new TokenAnillo(3);
        System.out.println("Nodo 1 antes: " + anillo.entrar(1));
        for (int i = 0; i < 3; i++) {
            System.out.println("Nodo " + i + ": " + anillo.entrar(i));
            anillo.salir(i);
            anillo.pasar(i);
        }
    }
}

// Salida esperada:
// Nodo 1 antes: false
// Nodo 0: true
// Nodo 1: true
// Nodo 2: true
