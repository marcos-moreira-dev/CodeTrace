// CodeTrace | Referencia: C++20 / GCC 13+
// Compilar/Comprobar: g++ -std=c++20 main.cpp estrategias.cpp -o app.exe
// Ejecutar: ./app.exe

#include "estrategia.hpp"
class Normal final : public Estrategia {
public: double total(double x) const override { return x; }
};
class Descuento final : public Estrategia {
public: double total(double x) const override { return x * 0.85; }
};
std::unique_ptr<Estrategia> normal() { return std::make_unique<Normal>(); }
std::unique_ptr<Estrategia> descuento() { return std::make_unique<Descuento>(); }
