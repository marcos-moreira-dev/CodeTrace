// CodeTrace | Referencia: C++20; g++ 13+.
// Comandos desde PowerShell, en una COPIA de la carpeta del paquete:
// Compilar/verificar: g++ -std=c++20 -Wall -Wextra '.\01-fundamentos/sintaxis\tipos.cpp' -o "$env:TEMP\codetrace-cpp.exe"
// Ejecutar: & "$env:TEMP\codetrace-cpp.exe"

#include <iostream>
#include <string>

int main() {
    std::string nombre = "CodeTrace";
    int ejercicios = 5;
    std::cout << nombre << ": " << ejercicios << '\n';
    return 0;
}
