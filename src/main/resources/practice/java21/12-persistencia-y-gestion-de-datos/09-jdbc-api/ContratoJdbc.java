// Tema:
// JDBC define interfaces estándar: Connection, PreparedStatement y ResultSet. La operación real recibe una conexión existente, usa parámetros y cierra statement/resultset, pero no la conexión que pertenece al llamador. La prueba usa un doble explícito del contrato de consulta, no un driver ni una base de datos.
// Modalidad: modelo teórico; no es un motor de bases de datos.
// Compilar: javac --release 21 -encoding UTF-8 ContratoJdbc.java MainConsultaSimulada.java
// Ejecutar: java MainConsultaSimulada

import java.sql.*;
import java.util.OptionalInt;

public class ContratoJdbc {
    @FunctionalInterface
    public interface ConsultaCantidad {
        OptionalInt buscar(String producto) throws SQLException;
    }

    public static ConsultaCantidad medianteJdbc(Connection conexion) {
        return producto -> {
            String sql = "SELECT cantidad FROM productos WHERE nombre = ?";
            try (PreparedStatement consulta = conexion.prepareStatement(sql)) {
                consulta.setString(1, producto);
                try (ResultSet filas = consulta.executeQuery()) {
                    if (!filas.next()) return OptionalInt.empty();
                    int cantidad = filas.getInt("cantidad");
                    if (filas.wasNull()) return OptionalInt.empty();
                    return OptionalInt.of(cantidad);
                }
            }
        };
    }

    public static String mostrar(ConsultaCantidad consulta, String producto) throws SQLException {
        OptionalInt resultado = consulta.buscar(producto);
        return resultado.isPresent() ? producto + ": " + resultado.getAsInt() : "No encontrado";
    }
}

// Salida esperada:
// pan: 12
// No encontrado
