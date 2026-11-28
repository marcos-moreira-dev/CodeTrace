// Tema:
// Barbero dormilón: una cola representa las sillas de espera. offer rechaza si no hay sitio; take espera si no hay clientes. Se llena la sala antes de iniciar el servicio para que el rechazo sea reproducible; un centinela cierra la barbería.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Barberia.java Barbero.java Cliente.java MainBarberia.java
// Ejecutar: java MainBarberia

public class Cliente {
    private final int id;
    public Cliente(int id) { this.id = id; }
    public boolean llegar(Barberia barberia) { return barberia.entrar(id); }
}

// Salida esperada:
// Cliente 1: true
// Cliente 2: true
// Cliente 3: false
// Atendidos: 2
