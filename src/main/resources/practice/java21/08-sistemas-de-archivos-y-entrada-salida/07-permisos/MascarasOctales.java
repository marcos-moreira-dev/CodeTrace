// Tema:
// Modelo POSIX: cada grupo de tres bits representa lectura (4), escritura (2) y ejecución (1).
// La máscara 0640 se interpreta como propietario rw-, grupo r-- y otros ---. No cambia permisos reales.
// Modalidad: M (R: real; M: mixta; P: dependiente de plataforma).
// Compilar: javac --release 21 -encoding UTF-8 MascarasOctales.java
// Ejecutar: java MascarasOctales


public class MascarasOctales {
    public static void main(String[] args) throws Exception {
        int modo = 0640;
        for (int desplazamiento = 6; desplazamiento >= 0; desplazamiento -= 3) {
            int grupo = (modo >> desplazamiento) & 7;
            String permisos = ((grupo & 4) != 0 ? "r" : "-")
                    + ((grupo & 2) != 0 ? "w" : "-")
                    + ((grupo & 1) != 0 ? "x" : "-");
            System.out.println(permisos);
        }
    }
}

// Salida esperada:
// rw-
// r--
// ---
