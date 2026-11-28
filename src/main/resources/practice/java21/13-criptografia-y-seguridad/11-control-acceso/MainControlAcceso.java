// Tema:
// Autorización por roles: después de autenticar la identidad, se determina qué operaciones puede realizar. La política aplica denegación por defecto, con roles y operaciones ficticios. No es un sistema real de autenticación.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 PermisosRoles.java MainControlAcceso.java
// Ejecutar: java MainControlAcceso

public class MainControlAcceso {
    public static void main(String[] args) throws Exception {
        PermisosRoles politica = new PermisosRoles();
        System.out.println("Cajero vende: " + politica.permite("CAJERO", "VENDER"));
        System.out.println("Cajero elimina: " + politica.permite("CAJERO", "ELIMINAR"));
        System.out.println("Admin elimina: " + politica.permite("ADMIN", "ELIMINAR"));
        System.out.println("Desconocido lee: " + politica.permite("OTRO", "LEER"));
    }
}

// Salida esperada:
// Cajero vende: true
// Cajero elimina: false
// Admin elimina: true
// Desconocido lee: false
