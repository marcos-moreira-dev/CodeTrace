// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — rescate de NPC y marcador de una partida virtual.
// Glosario: CT/T = bandos; MP9 = subfusil como objeto virtual; eco = ahorro.

#pragma once
#include "../command/Comandos.hpp"
#include <string>
#include <vector>
namespace cs {
enum class EstadoRehen { Retenido, Escoltado, Rescatado };
struct ObjetoVirtual {
    std::string nombre, categoria;
    int precio;
};
class Jugador {
    std::vector<ObjetoVirtual> inventario_;
    int creditos_ = 1600;
    int vida_ = 100, armadura_ = 35;
public:
    const std::string alias, bando;
    Jugador(std::string alias, std::string bando);
    int creditos() const { return creditos_; }
    int vida() const { return vida_; }
    int armadura() const { return armadura_; }
    const std::vector<ObjetoVirtual>& inventario() const { return inventario_; }
    bool comprar(const ObjetoVirtual& item);
    void preparar_ronda();
    void recompensar() { creditos_ += 300; }
};
class Rehen {
    const int calma_inicial_;
    EstadoRehen estado_ = EstadoRehen::Retenido;
    int calma_, progreso_ = 0;
    std::string escolta_;
public:
    // NPC del modo rescate, con ficha persistente durante la partida.
    const std::string nombre, ubicacion;
    const int integridad;
    Rehen(std::string nombre, std::string ubicacion, int integridad, int calma);
    int calma() const { return calma_; }
    int progreso() const { return progreso_; }
    const std::string& escolta() const { return escolta_; }
    EstadoRehen estado() const { return estado_; }
    void reiniciar();
    void rescatar(const Jugador& jugador);
};
struct Partida {
    std::string mapa;
    std::vector<Jugador> ct, t;
    std::vector<Rehen> rehenes;
    Marcador marcador{};
    std::vector<std::string> historial{};
    int reloj = 115;
    void iniciar_ronda();
    void registrar(const std::string& ganador, const std::string& motivo);
};
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
