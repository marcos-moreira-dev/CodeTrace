// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar en COPIA desde esta unidad: g++ -std=c++20 -Wall -Wextra -Werror *.cpp -o ejemplo
// Ejecutar: ./ejemplo (Linux/WSL) o .\ejemplo.exe (Windows)
// Contratos en .hpp, implementaciones en .cpp, un main.cpp cliente.

#include "Familias.hpp"
#include <memory>
#include <string>
#include <utility>

namespace {
struct BotonTexto final:Boton {
    std::string texto;
    explicit BotonTexto(std::string t):texto(std::move(t)){}
    std::string pintar()const override{return texto;}
};
struct DialogoTexto final:Dialogo {
    std::string texto;
    explicit DialogoTexto(std::string t):texto(std::move(t)){}
    std::string pintar()const override{return texto;}
};
struct Claro final:Fabrica {
    std::unique_ptr<Boton> boton()const override{
        return std::make_unique<BotonTexto>("[ OK ]");
    }
    std::unique_ptr<Dialogo> dialogo()const override{
        return std::make_unique<DialogoTexto>("Ventana clara");
    }
};
struct Oscuro final:Fabrica {
    std::unique_ptr<Boton> boton()const override{
        return std::make_unique<BotonTexto>("< OK >");
    }
    std::unique_ptr<Dialogo> dialogo()const override{
        return std::make_unique<DialogoTexto>("Ventana oscura");
    }
};
}
std::unique_ptr<Fabrica> fabricaClara(){return std::make_unique<Claro>();}
std::unique_ptr<Fabrica> fabricaOscura(){return std::make_unique<Oscuro>();}
