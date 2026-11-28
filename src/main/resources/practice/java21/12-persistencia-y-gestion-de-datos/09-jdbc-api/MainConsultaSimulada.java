// Tema:
// JDBC define interfaces estándar: Connection, PreparedStatement y ResultSet. La operación real recibe una conexión existente, usa parámetros y cierra statement/resultset, pero no la conexión que pertenece al llamador. La prueba usa un doble explícito del contrato de consulta, no un driver ni una base de datos.
// Modalidad: modelo teórico; no es un motor de bases de datos.
// Compilar: javac --release 21 -encoding UTF-8 ContratoJdbc.java MainConsultaSimulada.java
// Ejecutar: java MainConsultaSimulada

import java.util.Map;
import java.util.OptionalInt;

public class MainConsultaSimulada {
    public static void main(String[] args) throws Exception {
        Map<String,Integer> datos = Map.of("pan", 12, "leche", 7);
        // Doble de prueba: no implementa Connection ni ejecuta SQL.
        ContratoJdbc.ConsultaCantidad doble = producto -> {
            Integer cantidad = datos.get(producto);
            return cantidad == null ? OptionalInt.empty() : OptionalInt.of(cantidad);
        };
        System.out.println(ContratoJdbc.mostrar(doble, "pan"));
        System.out.println(ContratoJdbc.mostrar(doble, "arroz"));
    }
}

// Salida esperada:
// pan: 12
// No encontrado
