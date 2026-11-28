// Tema:
// Panadería de Lamport: los participantes eligen tickets; se ordena por (ticket,id), incluso cuando hay empate. Modelo de una fase de selección y entrada, con observación atómica y elección terminada; no demuestra por sí solo todo el algoritmo concurrente.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 PanaderiaSimulada.java MainPanaderia.java
// Ejecutar: java MainPanaderia

public class MainPanaderia {
    public static void main(String[] args) throws Exception {
        PanaderiaSimulada modelo = new PanaderiaSimulada(3);
        int maximo = modelo.observarMaximo();
        modelo.elegir(0, maximo);
        modelo.elegir(1, maximo); // Empate por observación concurrente simulada.
        modelo.elegir(2, modelo.observarMaximo());
        int id;
        while ((id = modelo.siguiente()) != -1) {
            System.out.println("Entra: " + id);
            modelo.salir(id);
        }
    }
}

// Salida esperada:
// Entra: 0
// Entra: 1
// Entra: 2
