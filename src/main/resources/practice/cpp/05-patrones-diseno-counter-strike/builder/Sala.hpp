// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — configurar una sala de rescate en cs_office.
// Patrón: Builder.
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

struct Sala { std::string mapa; int rondas; bool rehenes; };
class Configurador {
    Sala sala_{"de_dust2", 6, false};
public:
    Configurador& mapa(std::string nombre);
    Configurador& rondas(int n);
    Configurador& rescate(bool valor);
    Sala construir() const;
};
void demo_builder();

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
