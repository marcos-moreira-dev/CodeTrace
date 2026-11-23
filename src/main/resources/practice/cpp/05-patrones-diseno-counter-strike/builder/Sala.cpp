// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — configurar una sala de rescate en cs_office.
// Patrón: Builder.
// Glosario: CT/T = bandos; NPC = personaje no jugable; HUD = indicadores de pantalla.
// Implementación: enlazar este .cpp junto con main.cpp y los demás módulos.
#include "Sala.hpp"

namespace cs {

Configurador& Configurador::mapa(std::string nombre) { sala_.mapa = std::move(nombre); return *this; }

Configurador& Configurador::rondas(int n) { sala_.rondas = n; return *this; }

Configurador& Configurador::rescate(bool valor) { sala_.rehenes = valor; return *this; }

Sala Configurador::construir() const {
        // Devolvemos una copia: futuros cambios del builder no afectan a la sala.
        if (sala_.mapa.empty() || sala_.rondas <= 0) throw std::invalid_argument("Sala inválida");
        return sala_;
    }

void demo_builder() {
    auto s = Configurador().mapa("cs_office").rondas(12).rescate(true).construir();
    std::cout << "Sala: " << s.mapa << ", rondas=" << s.rondas
              << ", rehenes=" << (s.rehenes ? "true" : "false") << '\n';
}

} // namespace cs

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $fuentes=(Get-ChildItem .\05-patrones-diseno-counter-strike -Recurse -Filter *.cpp).FullName
 *   g++ -std=c++20 -Wall -Wextra $fuentes -o "$env:TEMP\ct-counter-strike.exe"
 *   & "$env:TEMP\ct-counter-strike.exe" builder
 * Salida esperada:
 *   Sala: cs_office, rondas=12, rehenes=true
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
