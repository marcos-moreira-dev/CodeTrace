// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — árbol de mapas de Counter-Strike.
// Patrón: Composite.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
// Implementación: enlazar este .cpp junto con main.cpp y los demás módulos.
#include "Recursos.hpp"

namespace cs {

void CarpetaMapa::agregar(std::unique_ptr<Recurso> hijo) { hijos_.push_back(std::move(hijo)); }

int ArchivoMapa::contar() const { return 1; }

int CarpetaMapa::contar() const {
        int total = 0;
        // Cada recurso, hoja o carpeta, implementa la misma operación virtual.
        for (const auto& hijo : hijos_) total += hijo->contar();
        return total;
    }

void demo_composite() {
    CarpetaMapa raiz;
    auto oficiales = std::make_unique<CarpetaMapa>();
    oficiales->agregar(std::make_unique<ArchivoMapa>("de_dust2"));
    oficiales->agregar(std::make_unique<ArchivoMapa>("de_inferno"));
    // move transfiere la propiedad de la subcarpeta al árbol.
    raiz.agregar(std::move(oficiales));
    raiz.agregar(std::make_unique<ArchivoMapa>("cs_office"));
    std::cout << "Mapas registrados: " << raiz.contar() << '\n';
}

} // namespace cs

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $fuentes=(Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
 *   g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
 *   & "$env:TEMP\ct-counter-strike.exe" composite
 * Salida esperada:
 *   Mapas registrados: 3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
