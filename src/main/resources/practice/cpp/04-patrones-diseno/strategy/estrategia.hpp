// CodeTrace | Referencia: C++20 / GCC 13+
// Compilar/Comprobar: g++ -std=c++20 main.cpp estrategias.cpp -o app.exe
// Ejecutar: ./app.exe

#ifndef CODETRACE_ESTRATEGIA_HPP
#define CODETRACE_ESTRATEGIA_HPP
#include <memory>
/** Contrato polimórfico cuya vida se gestiona mediante RAII. */
class Estrategia {
public:
    virtual ~Estrategia() = default;
    virtual double total(double subtotal) const = 0;
};
std::unique_ptr<Estrategia> normal();
std::unique_ptr<Estrategia> descuento();
#endif
