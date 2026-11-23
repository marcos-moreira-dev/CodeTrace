// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — registro reversible de una ronda CT.
// Patrón: Command.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
// Implementación: enlazar este .cpp junto con main.cpp y los demás módulos.
#include "Comandos.hpp"

namespace cs {

void SumarCt::ejecutar() {
        if (ejecutado_) throw std::logic_error("Ya ejecutado");
        // La referencia conserva el marcador original, no una copia de los puntos.
        ++receptor_.ct; ejecutado_ = true;
    }

void SumarCt::deshacer() {
        if (!ejecutado_) throw std::logic_error("No ejecutado");
        --receptor_.ct; ejecutado_ = false;
    }

void demo_command() {
    Marcador m;
    SumarCt comando(m);
    comando.ejecutar();
    std::cout << "Tras ejecutar: CT=" << m.ct << '\n';
    comando.deshacer();
    std::cout << "Tras deshacer: CT=" << m.ct << '\n';
}

} // namespace cs

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $fuentes=(Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
 *   g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
 *   & "$env:TEMP\ct-counter-strike.exe" command
 * Salida esperada:
 *   Tras ejecutar: CT=1
 *   Tras deshacer: CT=0
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
