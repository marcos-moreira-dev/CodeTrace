// Tema:
// Panadería de Lamport: los participantes eligen tickets; se ordena por (ticket,id), incluso cuando hay empate. Modelo de una fase de selección y entrada, con observación atómica y elección terminada; no demuestra por sí solo todo el algoritmo concurrente.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 PanaderiaSimulada.java MainPanaderia.java
// Ejecutar: java MainPanaderia

public class PanaderiaSimulada {
    private final int[] tickets;
    public PanaderiaSimulada(int n) { tickets = new int[n]; }
    public int observarMaximo() {
        int maximo = 0;
        for (int ticket : tickets) maximo = Math.max(maximo, ticket);
        return maximo;
    }
    public void elegir(int id, int maximoObservado) { tickets[id] = maximoObservado + 1; }
    public int siguiente() {
        int elegido = -1;
        for (int i = 0; i < tickets.length; i++) {
            if (tickets[i] == 0) continue;
            if (elegido == -1 || tickets[i] < tickets[elegido]) elegido = i;
        }
        return elegido;
    }
    public void salir(int id) { tickets[id] = 0; }
}

// Salida esperada:
// Entra: 0
// Entra: 1
// Entra: 2
