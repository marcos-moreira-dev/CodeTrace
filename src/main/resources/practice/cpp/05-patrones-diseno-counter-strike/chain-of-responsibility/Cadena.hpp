// CodeTrace | Referencia: C++20
// Compilar: g++ -std=c++20 -Wall -Wextra (véase guía al final).
// Dominio: Counter-Strike — avisos de rescate, minimapa e inventario virtual.
// Patrón: Chain of Responsibility.
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

struct Aviso { std::string tipo, detalle; };
class Manejador {
    std::string tipo_, prefijo_;
    std::unique_ptr<Manejador> siguiente_;
public:
    Manejador(std::string tipo, std::string prefijo)
        : tipo_(std::move(tipo)), prefijo_(std::move(prefijo)) {}
    Manejador& enlazar(std::unique_ptr<Manejador> otro);
    std::string atender(const Aviso& aviso) const;
};
std::unique_ptr<Manejador> crear_cadena();
void demo_cadena();

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
