// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — notificar a los HUD de espectadores.
// Patrón: Observer.
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

class MarcadorObservable {
    using Receptor = std::function<void(const std::string&, int)>;
    std::vector<Receptor> observadores_;
public:
    void suscribir(Receptor receptor);
    void anotar(const std::string& equipo, int puntos) const;
};
void demo_observer();

} // namespace cs

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $fuentes=(Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
 *   g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
 *   & "$env:TEMP\ct-counter-strike.exe" observer
 * Salida esperada:
 *   HUD: CT=2
 *   HUD: T=1
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
