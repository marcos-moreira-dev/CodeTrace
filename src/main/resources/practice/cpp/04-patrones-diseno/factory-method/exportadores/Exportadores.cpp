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
