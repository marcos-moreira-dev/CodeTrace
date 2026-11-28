// Tema:
// Un switch aprende la MAC origen en el puerto de entrada. Si el destino es desconocido inunda otros puertos; si está en el mismo puerto filtra. Modelo sin VLAN, STP ni caducidad.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 SwitchSimulado.java MainSwitch.java
// Ejecutar: java MainSwitch

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class SwitchSimulado {
    private final int puertos;
    private final HashMap<String,Integer> tabla = new HashMap<>();
    public SwitchSimulado(int puertos) { this.puertos = puertos; }
    public List<Integer> recibir(int entrada, String origen, String destino) {
        if (entrada < 1 || entrada > puertos) throw new IllegalArgumentException("Puerto");
        tabla.put(origen, entrada);
        Integer conocido = tabla.get(destino);
        if (conocido != null) return conocido == entrada ? List.of() : List.of(conocido);
        List<Integer> salida = new ArrayList<>();
        for (int p = 1; p <= puertos; p++) if (p != entrada) salida.add(p);
        return salida;
    }
}

// Salida esperada:
// [2, 3]
// [1]
// [2]
