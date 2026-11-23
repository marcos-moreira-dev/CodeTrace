// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — publicar puntuación CT como texto o CSV.
// Patrón: Factory Method.
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

using Exportador = std::function<std::string(int)>;
class CreadorReporte {
public:
    virtual ~CreadorReporte() = default;
    std::string publicar(int puntos) const {
        // Las variantes eligen el exportador; el flujo publicar es común.
        return crear()(puntos);
    }
protected:
    virtual Exportador crear() const = 0;
};
class CreadorTexto final : public CreadorReporte {
    Exportador crear() const override {
        return [](int p) { return "CT=" + std::to_string(p); };
    }
};
class CreadorCsv final : public CreadorReporte {
    Exportador crear() const override {
        return [](int p) { return "equipo,rondas;CT," + std::to_string(p); };
    }
};
inline void demo_factory_method() {
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
