// CodeTrace | Referencia: C++20, proyecto Observer
// Compilar: g++ -std=c++20 07-proyectos/observer/main.cpp -o observer
#include "CentroEventos.hpp"
#include <iostream>
struct Consola final : IObservador {
    void actualizar(const std::string& e) override { std::cout << e << '\n'; }
};
int main() {
    CentroEventos centro;
    Consola pantalla;
    centro.registrar(pantalla);
    centro.publicar("Factura 001");
}
