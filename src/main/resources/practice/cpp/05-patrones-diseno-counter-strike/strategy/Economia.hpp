// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — eco = fase de ahorro de créditos virtuales.
// Patrón: Strategy.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
// Cabecera: declara la interfaz; las operaciones principales se definen en el .cpp.
#pragma once
#include <iostream>
#include <string>
#include <vector>
#include <memory>
#include <functional>
#include <utility>
#include <stdexcept>
namespace cs {

std::string eco(int p);
std::string equipo(int p);
class Economia {
    std::function<std::string(int)> politica_;
public:
    explicit Economia(std::function<std::string(int)> politica) : politica_(std::move(politica)) {}
    void cambiar(std::function<std::string(int)> politica);
    std::string plan(int creditos) const;
};
void demo_strategy();

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
