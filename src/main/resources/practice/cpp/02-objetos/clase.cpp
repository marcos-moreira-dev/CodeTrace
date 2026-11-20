// CodeTrace | Referencia: C++20; g++ 13+.
// Comandos desde PowerShell, en una COPIA de la carpeta del paquete:
// Compilar/verificar: g++ -std=c++20 -Wall -Wextra '.\02-objetos\clase.cpp' -o "$env:TEMP\codetrace-cpp.exe"
// Ejecutar: & "$env:TEMP\codetrace-cpp.exe"

#include <iostream>

class Contador {
    int valor = 0;
public:
    void incrementar() { ++valor; }
    int obtener() const { return valor; }
};

int main() {
    Contador contador;
    contador.incrementar();
    std::cout << contador.obtener() << '\n';
}
