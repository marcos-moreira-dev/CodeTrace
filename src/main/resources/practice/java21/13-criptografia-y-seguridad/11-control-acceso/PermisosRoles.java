// Tema:
// Autorización por roles: después de autenticar la identidad, se determina qué operaciones puede realizar. La política aplica denegación por defecto, con roles y operaciones ficticios. No es un sistema real de autenticación.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 PermisosRoles.java MainControlAcceso.java
// Ejecutar: java MainControlAcceso

import java.util.Map;
import java.util.Set;

public class PermisosRoles {
    private final Map<String, Set<String>> permisos = Map.of(
        "CAJERO", Set.of("LEER", "VENDER"),
        "ADMIN", Set.of("LEER", "VENDER", "ELIMINAR")
    );
    public boolean permite(String rol, String accion) {
        return permisos.getOrDefault(rol, Set.of()).contains(accion);
    }
}

// Salida esperada:
// Cajero vende: true
// Cajero elimina: false
// Admin elimina: true
// Desconocido lee: false
