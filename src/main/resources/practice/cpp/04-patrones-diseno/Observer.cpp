// CodeTrace | Referencia: C++20, g++ 13+.
// Desde PowerShell en una COPIA del paquete:
// Compilar: g++ -std=c++20 -Wall -Wextra '.\04-patrones-diseno\Observer.cpp' -o "$env:TEMP\codetrace-cpp.exe"
// Ejecutar: & "$env:TEMP\codetrace-cpp.exe"

#include <functional>
#include <iostream>
#include <vector>
class Emisor {
    std::vector<std::function<void(int)>> oyentes;
public:
    void suscribir(std::function<void(int)> escucha) { oyentes.push_back(escucha); }
    void actualizar(int valor) {
        for (const auto& escucha : oyentes) escucha(valor);
    }
};
int main() {
    Emisor emisor;
    emisor.suscribir([](int n) { std::cout << "Nuevo valor: " << n << '\n'; });
    emisor.actualizar(42);
    return 0;
}
