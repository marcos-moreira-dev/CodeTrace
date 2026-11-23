// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — configurar una sala de rescate en cs_office.
// Patrón: Builder.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
#pragma once
#include <iostream>
#include <string>
#include <vector>
#include <memory>
#include <functional>
#include <utility>
#include <stdexcept>
namespace cs {

struct Sala { std::string mapa; int rondas; bool rehenes; };
class Configurador {
    Sala sala_{"de_dust2", 6, false};
public:
    Configurador& mapa(std::string nombre) { sala_.mapa = std::move(nombre); return *this; }
    Configurador& rondas(int n) { sala_.rondas = n; return *this; }
    Configurador& rescate(bool valor) { sala_.rehenes = valor; return *this; }
    Sala construir() const {
        // Devolvemos una copia: futuros cambios del builder no afectan a la sala.
        if (sala_.mapa.empty() || sala_.rondas <= 0) throw std::invalid_argument("Sala inválida");
        return sala_;
    }
};
inline void demo_builder() {
    auto s = Configurador().mapa("cs_office").rondas(12).rescate(true).construir();
    std::cout << "Sala: " << s.mapa << ", rondas=" << s.rondas
              << ", rehenes=" << (s.rehenes ? "true" : "false") << '\n';
}

} // namespace cs

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $fuentes=(Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
 *   g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
 *   & "$env:TEMP\ct-counter-strike.exe" builder
 * Salida esperada:
 *   Sala: cs_office, rondas=12, rehenes=true
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
