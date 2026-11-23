// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — avisos de rescate, minimapa e inventario virtual.
// Patrón: Chain of Responsibility.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
// Implementación: enlazar este .cpp junto con main.cpp y los demás módulos.
#include "Cadena.hpp"

namespace cs {

Manejador& Manejador::enlazar(std::unique_ptr<Manejador> otro) {
        // La cadena posee el siguiente eslabón; no usamos delete manual.
        siguiente_ = std::move(otro);
        return *siguiente_;
    }

std::string Manejador::atender(const Aviso& aviso) const {
        if (aviso.tipo == tipo_) return prefijo_ + ": " + aviso.detalle;
        // El mismo aviso se pasa por referencia hasta que alguien lo reconoce.
        return siguiente_ ? siguiente_->atender(aviso) : "Sin responsable: " + aviso.detalle;
    }

std::unique_ptr<Manejador> crear_cadena() {
    auto primero = std::make_unique<Manejador>("rehen", "Rescate");
    primero->enlazar(std::make_unique<Manejador>("rival", "Reconocimiento"))
           .enlazar(std::make_unique<Manejador>("equipo", "Inventario"));
    return primero;
}

void demo_cadena() {
    auto cadena = crear_cadena();
    // Son eventos de la partida; todos atraviesan el mismo punto de entrada.
    const std::vector<Aviso> avisos{
        {"rehen", "rehén virtual liberado"}, {"rival", "rival marcado en el minimapa"},
        {"equipo", "MP9 registrado como objeto virtual"}, {"otro", "evento del servidor no reconocido"}};
    for (const auto& aviso : avisos) std::cout << cadena->atender(aviso) << '\n';
}

} // namespace cs

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $fuentes=(Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
 *   g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
 *   & "$env:TEMP\ct-counter-strike.exe" chain-of-responsibility
 * Salida esperada:
 *   Rescate: rehén virtual liberado
 *   Reconocimiento: rival marcado en el minimapa
 *   Inventario: MP9 registrado como objeto virtual
 *   Sin responsable: evento del servidor no reconocido
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
