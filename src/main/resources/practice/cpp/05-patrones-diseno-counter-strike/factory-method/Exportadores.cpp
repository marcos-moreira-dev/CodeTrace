// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — publicar puntuación CT como texto o CSV.
// Patrón: Factory Method.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
// Implementación: enlazar este .cpp junto con main.cpp y los demás módulos.
#include "Exportadores.hpp"

namespace cs {

std::string CreadorReporte::publicar(int puntos) const {
        // Las variantes eligen el exportador; el flujo publicar es común.
        return crear()(puntos);
    }

Exportador CreadorTexto::crear() const {
        return [](int p) { return "CT=" + std::to_string(p); };
    }

Exportador CreadorCsv::crear() const {
        return [](int p) { return "equipo,rondas;CT," + std::to_string(p); };
    }

void demo_factory_method() {
    std::cout << "Texto: " << CreadorTexto().publicar(7) << '\n';
    std::cout << "CSV: " << CreadorCsv().publicar(7) << '\n';
}

} // namespace cs

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $fuentes=(Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
 *   g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
 *   & "$env:TEMP\ct-counter-strike.exe" factory-method
 * Salida esperada:
 *   Texto: CT=7
 *   CSV: equipo,rondas;CT,7
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
