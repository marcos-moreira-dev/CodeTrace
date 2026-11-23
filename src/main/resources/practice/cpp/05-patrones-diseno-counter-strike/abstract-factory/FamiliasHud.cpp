// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — HUD de marcador e inventario con temas compatibles.
// Patrón: Abstract Factory.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
// Implementación: enlazar este .cpp junto con main.cpp y los demás módulos.
#include "FamiliasHud.hpp"

namespace cs {

std::string MarcadorTema::mostrar(int p) const {
        return "[" + tema_ + "] CT=" + std::to_string(p);
    }

std::string InventarioTema::mostrar(const std::string& item) const {
        return "[" + tema_ + "] equipo=" + item;
    }

std::unique_ptr<EtiquetaMarcador> FamiliaTema::marcador() const {
        return std::make_unique<MarcadorTema>(tema_);
    }

std::unique_ptr<EtiquetaInventario> FamiliaTema::inventario() const {
        return std::make_unique<InventarioTema>(tema_);
    }

void demo_abstract_factory() {
    for (const auto& [nombre, tema] : std::vector<std::pair<std::string,std::string>>{
            {"Claro","CLARO"}, {"Oscuro","OSCURO"}}) {
        FamiliaTema familia(tema);
        std::cout << nombre << ": " << familia.marcador()->mostrar(5)
                  << " | " << familia.inventario()->mostrar("MP9") << '\n';
    }
}

} // namespace cs

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $fuentes=(Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
 *   g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
 *   & "$env:TEMP\ct-counter-strike.exe" abstract-factory
 * Salida esperada:
 *   Claro: [CLARO] CT=5 | [CLARO] equipo=MP9
 *   Oscuro: [OSCURO] CT=5 | [OSCURO] equipo=MP9
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
