// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — registro reversible de una ronda CT.
// Patrón: Command.
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

struct Marcador { int ct = 0, t = 0; };
struct Comando {
    virtual ~Comando() = default;
    virtual void ejecutar() = 0;
    virtual void deshacer() = 0;
};
class SumarCt final : public Comando {
    Marcador& receptor_;
    bool ejecutado_ = false;
public:
    explicit SumarCt(Marcador& receptor) : receptor_(receptor) {}
    void ejecutar() override {
        if (ejecutado_) throw std::logic_error("Ya ejecutado");
        // La referencia conserva el marcador original, no una copia de los puntos.
        ++receptor_.ct; ejecutado_ = true;
    }
    void deshacer() override {
        if (!ejecutado_) throw std::logic_error("No ejecutado");
        --receptor_.ct; ejecutado_ = false;
    }
};
inline void demo_command() {
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
