// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — ping = latencia de la conexión al servidor.
// Patrón: Adapter.
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

struct TelemetriaLegada {
    int microsegundos() const { return 42000; }
};
class AdaptadorPing {
    const TelemetriaLegada& origen_;
public:
    explicit AdaptadorPing(const TelemetriaLegada& origen) : origen_(origen) {}
    // La referencia no posee el servidor: debe vivir más que este adaptador.
    std::string mostrar() const;
};
void demo_adapter();

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
