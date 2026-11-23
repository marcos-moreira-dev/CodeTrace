// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — ping = latencia de la conexión al servidor.
// Patrón: Adapter.
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

struct TelemetriaLegada {
    int microsegundos() const { return 42000; }
};
class AdaptadorPing {
    const TelemetriaLegada& origen_;
public:
    explicit AdaptadorPing(const TelemetriaLegada& origen) : origen_(origen) {}
    // La referencia no posee el servidor: debe vivir más que este adaptador.
    std::string mostrar() const {
        return "Ping: " + std::to_string(origen_.microsegundos() / 1000) + " ms";
    }
};
inline void demo_adapter() {
    TelemetriaLegada servidor;
    std::cout << AdaptadorPing(servidor).mostrar() << '\n';
}

} // namespace cs

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $fuentes=(Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
 *   g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
 *   & "$env:TEMP\ct-counter-strike.exe" adapter
 * Salida esperada:
 *   Ping: 42 ms
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
