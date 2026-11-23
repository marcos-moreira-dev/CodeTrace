// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — rescate de NPC y marcador de una partida virtual.
// Glosario: CT/T = bandos; MP9 = subfusil como objeto virtual; eco = ahorro.

#include "Modelo.hpp"
#include <algorithm>
#include <stdexcept>
#include <utility>
namespace cs {
Jugador::Jugador(std::string alias, std::string bando)
    : alias(std::move(alias)), bando(std::move(bando)) {}
bool Jugador::comprar(const ObjetoVirtual& item) {
    // La ficha MP9 es un objeto virtual. Si faltan créditos, no se modifica nada.
    if (item.precio < 0 || creditos_ < item.precio) return false;
    creditos_ -= item.precio;
    inventario_.push_back(item);
    return true;
}
void Jugador::preparar_ronda() { vida_ = 100; armadura_ = 35; }
Rehen::Rehen(std::string nombre, std::string ubicacion, int integridad, int calma)
    : calma_inicial_(calma), calma_(calma), nombre(std::move(nombre)),
      ubicacion(std::move(ubicacion)), integridad(integridad) {}
void Rehen::reiniciar() {
    // Elena y Omar conservan sus fichas; solo vuelve a estar pendiente el objetivo.
    estado_ = EstadoRehen::Retenido; calma_ = calma_inicial_;
    progreso_ = 0; escolta_.clear();
}
void Rehen::rescatar(const Jugador& jugador) {
    if (jugador.bando != "CT" || estado_ != EstadoRehen::Retenido)
        throw std::logic_error("Escolta inválida");
    escolta_ = jugador.alias;
    calma_ = std::min(100, calma_ + 12);
    estado_ = EstadoRehen::Escoltado;
    // Estado abstracto del NPC; no representa coordenadas ni rutas reales.
    progreso_ = 100; estado_ = EstadoRehen::Rescatado;
}
void Partida::iniciar_ronda() {
    reloj = 115;
    // auto& modifica las fichas reales, a diferencia de auto por valor.
    for (auto& jugador : ct) jugador.preparar_ronda();
    for (auto& jugador : t) jugador.preparar_ronda();
    for (auto& rehen : rehenes) rehen.reiniciar();
}
void Partida::registrar(const std::string& ganador, const std::string& motivo) {
    if (ganador == "CT") SumarCt(marcador).ejecutar();
    else ++marcador.t;
    // El texto del resultado sobrevive al reinicio del NPC.
    historial.push_back(ganador + ": " + motivo);
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
