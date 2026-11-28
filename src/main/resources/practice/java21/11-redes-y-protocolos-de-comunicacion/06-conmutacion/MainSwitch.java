// Tema:
// Un switch aprende la MAC origen en el puerto de entrada. Si el destino es desconocido inunda otros puertos; si está en el mismo puerto filtra. Modelo sin VLAN, STP ni caducidad.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 SwitchSimulado.java MainSwitch.java
// Ejecutar: java MainSwitch

public class MainSwitch {
    public static void main(String[] args) throws Exception {
        SwitchSimulado sw = new SwitchSimulado(3);
        System.out.println(sw.recibir(1, "A", "B"));
        System.out.println(sw.recibir(2, "B", "A"));
        System.out.println(sw.recibir(1, "A", "B"));
    }
}

// Salida esperada:
// [2, 3]
// [1]
// [2]
