// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — rescate de NPC y marcador de una partida virtual.
// Glosario: CT/T = bandos; MP9 = subfusil como objeto virtual; eco = ahorro.

#include "Vista.hpp"
#include <iostream>
namespace cs {
static std::string alias_del_equipo(const std::vector<Jugador>& equipo) {
    std::string texto;
    // Leemos cada jugador por referencia constante sin copiar su inventario.
    for (const auto& jugador : equipo) {
        if (!texto.empty()) texto += ", ";
        texto += jugador.alias;
    }
    return texto;
}
void VistaConsola::presentar(const Partida& p, int rondas, const std::string& ping, int recursos) const {
    std::cout << "=== COUNTER-STRIKE | PARTIDA DIDÁCTICA ===\n"
              << "Mapa: " << p.mapa << " | Rondas: " << rondas << " | " << ping << '\n'
              << "Equipos: CT (" << alias_del_equipo(p.ct) << ") vs T (" << alias_del_equipo(p.t) << ")\n"
              << "Mapas registrados: " << recursos << '\n';
}
void VistaConsola::ficha(const Rehen& r) const {
    const char* estado = r.estado() == EstadoRehen::Rescatado ? "RESCATADO" :
        r.estado() == EstadoRehen::Escoltado ? "ESCOLTADO" : "RETENIDO";
    std::cout << "Rehén " << r.nombre << ": " << estado << " | escolta=" << r.escolta()
              << " | calma=" << r.calma() << " | integridad=" << r.integridad << '\n';
}
void VistaConsola::marcador(const std::string& equipo, int puntos) {
    // Observer publica CT antes de T. Solo la segunda notificación imprime ambos.
    if (equipo == "CT") ct_ = puntos;
    else std::cout << "Marcador: CT " << ct_ << " - " << puntos << " T\n";
}
void VistaConsola::finalizar(const Partida& p, const std::string& reporte) const {
    const char* resultado = p.marcador.ct > p.marcador.t ? "Gana CT" :
        p.marcador.t > p.marcador.ct ? "Gana T" : "Empate";
    std::cout << "=== FIN DE LA PARTIDA ===\nResultado: " << resultado
              << "\nCréditos Astra: " << p.ct.front().creditos()
              << "\nReporte: " << reporte << '\n';
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
