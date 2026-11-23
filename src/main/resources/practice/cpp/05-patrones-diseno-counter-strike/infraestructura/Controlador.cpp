// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — rescate de NPC y marcador de una partida virtual.
// Glosario: CT/T = bandos; MP9 = subfusil como objeto virtual; eco = ahorro.

#include "Controlador.hpp"
#include "../adapter/Ping.hpp"
#include "../builder/Sala.hpp"
#include "../chain-of-responsibility/Cadena.hpp"
#include "../composite/Recursos.hpp"
#include "../factory-method/Exportadores.hpp"
#include "../observer/MarcadorObservable.hpp"
#include "../strategy/Economia.hpp"
#include <array>
namespace cs {
Partida ControladorPartida::simular() {
    auto sala = Configurador().mapa("cs_office").rondas(3).rescate(true).construir();
    Partida p{sala.mapa, {Jugador("Astra","CT"), Jugador("Nilo","CT")},
        {Jugador("Rook","T"), Jugador("Vega","T")},
        {Rehen("Elena","Archivo",92,38), Rehen("Omar","Sala de juntas",86,24)}};
    // Astra es una referencia al elemento del roster, no un jugador duplicado.
    // No redimensionamos el vector mientras la referencia siga en uso.
    auto& astra = p.ct.front();
    TelemetriaLegada servidor;
    CarpetaMapa recursos;
    auto oficiales = std::make_unique<CarpetaMapa>();
    oficiales->agregar(std::make_unique<ArchivoMapa>("cs_office"));
    recursos.agregar(std::move(oficiales));
    recursos.agregar(std::make_unique<ArchivoMapa>("de_dust2"));
    vista_.presentar(p, sala.rondas, AdaptadorPing(servidor).mostrar(), recursos.contar());
    auto cadena = crear_cadena();
    MarcadorObservable observable;
    // La captura referencia una vista que vive durante toda la simulación.
    // El observable local se destruye antes de que termine la fachada.
    observable.suscribir([this](const std::string& bando, int puntos) {
        vista_.marcador(bando, puntos);
    });
    Economia economia(eco);
    // Guion equivalente a la semilla 42 de Java: evita divergencias de RNG.
    const std::array<bool, 3> desenlaces{true, false, true};
    std::size_t indice_rehen = 0;
    for (int numero = 1; numero <= sala.rondas; ++numero) {
        p.iniciar_ronda();
        std::cout << "--- RONDA " << numero << " ---\n";
        economia.cambiar(numero % 2 ? eco : equipo);
        std::cout << economia.plan(astra.creditos()) << '\n';
        if (numero % 2 == 0 && astra.comprar({"MP9","subfusil",950}))
            std::cout << cadena->atender({"equipo","MP9 registrado como objeto virtual"}) << '\n';
        if (desenlaces.at(static_cast<std::size_t>(numero - 1))) {
            // El índice elige el NPC almacenado; auto& permite cambiarlo in situ.
            auto& rehen = p.rehenes.at(indice_rehen++ % p.rehenes.size());
            rehen.rescatar(astra);
            p.reloj = 60;
            std::cout << cadena->atender({"rehen", rehen.nombre + " llegó a la zona de extracción"}) << '\n';
            vista_.ficha(rehen);
            p.registrar("CT", "rescate de " + rehen.nombre);
        } else {
            p.reloj = 0;
            std::cout << "Tiempo agotado sin rescate\n";
            p.registrar("T", "objetivo sin completar");
        }
        astra.recompensar();
        observable.anotar("CT", p.marcador.ct);
        observable.anotar("T", p.marcador.t);
    }
    vista_.finalizar(p, CreadorTexto().publicar(p.marcador.ct));
    return p; // El modelo se devuelve por valor; no quedan referencias a variables locales.
}
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $fuentes=(Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
 *   g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
 *   & "$env:TEMP\ct-counter-strike.exe"
 * Salida esperada:
 *   === COUNTER-STRIKE | PARTIDA DIDÁCTICA ===
 *   Mapa: cs_office | Rondas: 3 | Ping: 42 ms
 *   Equipos: CT (Astra, Nilo) vs T (Rook, Vega)
 *   Mapas registrados: 2
 *   --- RONDA 1 ---
 *   Eco: guardar 1600 créditos
 *   Rescate: Elena llegó a la zona de extracción
 *   Rehén Elena: RESCATADO | escolta=Astra | calma=50 | integridad=92
 *   Marcador: CT 1 - 0 T
 *   --- RONDA 2 ---
 *   Equipo: asignar 1900 créditos virtuales
 *   Inventario: MP9 registrado como objeto virtual
 *   Tiempo agotado sin rescate
 *   Marcador: CT 1 - 1 T
 *   --- RONDA 3 ---
 *   Eco: guardar 1250 créditos
 *   Rescate: Omar llegó a la zona de extracción
 *   Rehén Omar: RESCATADO | escolta=Astra | calma=36 | integridad=86
 *   Marcador: CT 2 - 1 T
 *   === FIN DE LA PARTIDA ===
 *   Resultado: Gana CT
 *   Créditos Astra: 1550
 *   Reporte: CT=2
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
