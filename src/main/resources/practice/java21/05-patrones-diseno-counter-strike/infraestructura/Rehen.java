// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp $out codetrace.practica.counterstrike.MainCounterStrike
// Dominio: Counter-Strike, simulador narrativo de 3 rondas automáticas.
// Glosario: CT = antiterroristas; T = terroristas; PV = vida del jugador.
// Patrón de organización: infraestructura MVC, no un nuevo patrón GoF.
package codetrace.practica.counterstrike.infraestructura;

import java.util.Objects;

/**
 * Modelo didáctico de un rehén de Counter-Strike.
 *
 * Un rehén es un personaje NO controlado por el usuario (NPC) que aparece
 * en mapas de rescate, por ejemplo cs_office. Aquí tiene nombre, ubicación,
 * integridad (0-100), calma (0-100), un jugador que lo escolta y progreso
 * hacia la zona segura (0-100 %). Son variables inventadas para practicar
 * encapsulación, validaciones y transiciones de estado.
 *
 * No hay animación ni simulación física: avanzarHaciaSalida() solo aumenta
 * un porcentaje descriptivo en la consola.
 */
public final class Rehen {
    private final String nombre;
    // Lugar de aparición del NPC al iniciar la ronda. Es una etiqueta fija,
    // no cambia durante la escolta; el avance se guarda en progreso/estado.
    private final String ubicacion;
    private final int integridadInicial;
    private final int calmaInicial;
    private int integridad;
    private int calma;
    private int progreso;
    private String escolta = "ninguna";
    private EstadoRehen estado = EstadoRehen.RETENIDO;

    public Rehen(String nombre, String ubicacion, int integridad, int calma) {
        if (nombre == null || nombre.isBlank()
                || ubicacion == null || ubicacion.isBlank()
                || integridad < 0 || integridad > 100
                || calma < 0 || calma > 100)
            throw new IllegalArgumentException("Ficha del rehén inválida");
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        // Guardamos los atributos iniciales para que el siguiente reinicio
        // vuelva a colocar al mismo NPC en las condiciones de partida.
        this.integridadInicial = integridad;
        this.calmaInicial = calma;
        reiniciar();
    }

    public String nombre() { return nombre; }
    public String ubicacion() { return ubicacion; }
    public int integridad() { return integridad; }
    public int calma() { return calma; }
    public int progreso() { return progreso; }
    public String escolta() { return escolta; }
    public EstadoRehen estado() { return estado; }

    public void reiniciar() {
        // Al comenzar una nueva ronda, el objetivo de rescate vuelve a
        // estar pendiente: recupera su calma e integridad iniciales, pierde
        // la asignación anterior de escolta y su progreso vuelve a cero.
        // No se crea otro objeto ni se borra el historial de EstadoPartida.
        integridad = integridadInicial;
        calma = calmaInicial;
        progreso = 0;
        escolta = "ninguna";
        estado = EstadoRehen.RETENIDO;
    }

    public void tranquilizar(int puntos) {
        if (puntos < 0) throw new IllegalArgumentException("Puntos negativos");
        // En nuestro dominio, la calma representa el estado narrativo del
        // NPC; Math.min limita el atributo a 100 tras sumar el incremento.
        calma = Math.min(100, calma + puntos);
    }

    public void iniciarEscolta(Jugador jugador) {
        Objects.requireNonNull(jugador, "jugador");
        // Solo un CT puede comenzar la escolta y solo si el rehén estaba
        // RETENIDO. Esto impide rescatarlo dos veces por accidente.
        if (estado != EstadoRehen.RETENIDO || jugador.bando() != Bando.CT)
            throw new IllegalStateException("No se puede iniciar esa escolta");
        // Registramos el alias del CT dentro de la ficha del NPC para
        // mostrar luego quién lo acompañó en la narración de la ronda.
        escolta = jugador.alias();
        estado = EstadoRehen.ESCOLTADO;
    }

    public void avanzarHaciaSalida(int porcentaje) {
        if (estado != EstadoRehen.ESCOLTADO || porcentaje <= 0)
            throw new IllegalStateException("Primero debe comenzar la escolta");
        // El avance es un indicador abstracto del objetivo de evacuación.
        // Un porcentaje de 100 equivale a entregar el NPC en la zona segura
        // del escenario narrativo; no almacenamos coordenadas ni trayectos.
        // El controlador leerá RESCATADO para relatar el objetivo completado.
        progreso = Math.min(100, progreso + porcentaje);
        if (progreso == 100) estado = EstadoRehen.RESCATADO;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.MainCounterStrike
 * Elena y Omar muestran por separado calma, integridad, escolta, progreso y estado.
 * Salida esperada:
 *   [REHÉN] Elena | ubicación=Archivo de oficinas | calma=50/100 | integridad=92/100 | escolta=Astra | progreso=100% | estado=RESCATADO
 *   [REHÉN] Omar | ubicación=Sala de juntas | calma=36/100 | integridad=86/100 | escolta=Astra | progreso=100% | estado=RESCATADO
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
