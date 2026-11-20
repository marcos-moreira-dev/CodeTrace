// CodeTrace | Referencia: C++20 / GCC g++ 13+ / Linux o MinGW compatible
// Compilar en COPIA desde esta carpeta: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL), o .\ejemplo.exe (Windows)
// Interfaz en .hpp, implementación en .cpp, cliente en main.cpp.

#include "Exportadores.hpp"
#include <memory>

namespace {
class ExportadorTexto final : public Exportador {
public:
    std::string exportar(const std::string& d) const override {
        return "TXT: " + d;
    }
};
}

std::string Creador::preparar(const std::string& d) const {
    auto producto = crear();
    return producto->exportar(d);
}
std::unique_ptr<Exportador> CreadorTexto::crear() const {
    return std::make_unique<ExportadorTexto>();
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\04-patrones-diseno\factory-method\exportadores\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: Exportadores.cpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   TXT: Factura 001
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
