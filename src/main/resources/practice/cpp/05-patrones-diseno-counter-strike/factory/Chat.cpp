// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — mensajes del chat de equipo.
// Patrón: Simple Factory.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
// Implementación: enlazar este .cpp junto con main.cpp y los demás módulos.
#include "Chat.hpp"

namespace cs {

std::string MensajeChat::mostrar() const { return "Chat de equipo: " + contenido_; }

std::unique_ptr<MensajeSala> crear_aviso(std::string texto) {
    // El cliente posee un mensaje mediante el contrato abstracto.
    return std::make_unique<MensajeChat>(std::move(texto));
}

void demo_factory() { std::cout << crear_aviso("Ronda iniciada")->mostrar() << '\n'; }

} // namespace cs

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $fuentes=(Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
 *   g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
 *   & "$env:TEMP\ct-counter-strike.exe" factory
 * Salida esperada:
 *   Chat de equipo: Ronda iniciada
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
