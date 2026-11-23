// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — puntuaciones ficticias de torneos.
// Patrón: Strategy.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
// Implementación: enlazar este .cpp junto con main.cpp y los demás módulos.
#include "Reglas.hpp"

namespace cs {

int puntos(int victorias, const std::function<int(int)>& regla) {
    return regla(victorias);
}

void demo_puntuacion() {
    // Dos políticas inventadas, no reglas oficiales del juego.
    std::cout << "Casual: " << puntos(2, [](int v) { return v * 2; }) << " puntos\n";
    std::cout << "Competitivo: " << puntos(2, [](int v) { return v * 3; }) << " puntos\n";
}

} // namespace cs

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $fuentes=(Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
 *   g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
 *   & "$env:TEMP\ct-counter-strike.exe" strategy-puntuacion
 * Salida esperada:
 *   Casual: 4 puntos
 *   Competitivo: 6 puntos
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
