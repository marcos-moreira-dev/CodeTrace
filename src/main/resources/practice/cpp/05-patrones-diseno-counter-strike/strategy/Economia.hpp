// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — eco = fase de ahorro de créditos virtuales.
// Patrón: Strategy.
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

inline std::string eco(int p) { return "Eco: guardar " + std::to_string(p) + " créditos"; }
inline std::string equipo(int p) { return "Equipo: asignar " + std::to_string(p) + " créditos virtuales"; }
class Economia {
    std::function<std::string(int)> politica_;
public:
    explicit Economia(std::function<std::string(int)> politica) : politica_(std::move(politica)) {}
    void cambiar(std::function<std::string(int)> politica) { politica_ = std::move(politica); }
    std::string plan(int creditos) const { return politica_(creditos); }
};
inline void demo_strategy() {
    Economia economia(eco);
    std::cout << economia.plan(1500) << '\n';
    // Cambia la función invocada, no el saldo del personaje virtual.
    economia.cambiar(equipo);
    std::cout << economia.plan(1500) << '\n';
}

} // namespace cs

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $fuentes=(Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
 *   g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
 *   & "$env:TEMP\ct-counter-strike.exe" strategy
 * Salida esperada:
 *   Eco: guardar 1500 créditos
 *   Equipo: asignar 1500 créditos virtuales
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
