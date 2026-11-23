// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — etiquetas de ronda y urgencia de un aviso.
// Patrón: Decorator.
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

using AvisoEquipo = std::function<std::string()>;
inline AvisoEquipo urgente(AvisoEquipo aviso) {
    // Captura por valor: el aviso anterior sobrevive al retorno de la función.
    return [aviso = std::move(aviso)] { return "[URGENTE] " + aviso(); };
}
inline AvisoEquipo de_ronda(AvisoEquipo aviso, int ronda) {
    return [aviso = std::move(aviso), ronda] {
        return "[RONDA " + std::to_string(ronda) + "] " + aviso();
    };
}
inline void demo_decorator() {
    auto mensaje = de_ronda(urgente([] { return std::string("Rehén virtual rescatado"); }), 12);
    std::cout << mensaje() << '\n';
}

} // namespace cs

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $fuentes=(Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
 *   g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
 *   & "$env:TEMP\ct-counter-strike.exe" decorator
 * Salida esperada:
 *   [RONDA 12] [URGENTE] Rehén virtual rescatado
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
