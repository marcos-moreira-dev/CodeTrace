// CodeTrace | Referencia: C++20, g++ 13+.
// Desde PowerShell en una COPIA del paquete:
// Compilar: g++ -std=c++20 -Wall -Wextra '.\05-algoritmos\Euclides.cpp' -o "$env:TEMP\codetrace-cpp.exe"
// Ejecutar: & "$env:TEMP\codetrace-cpp.exe"

#include <iostream>
#include <numeric>
int main() {
    std::cout << "MCD: " << std::gcd(48, 18) << '\n';
    return 0;
}
