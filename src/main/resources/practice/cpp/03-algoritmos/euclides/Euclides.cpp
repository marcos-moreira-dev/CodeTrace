// CodeTrace | Referencia: C++20, g++ 13+.
// Desde PowerShell en una COPIA del paquete:
// Compilar: g++ -std=c++20 -Wall -Wextra '.\03-algoritmos\euclides\Euclides.cpp' -o "$env:TEMP\codetrace-cpp.exe"
// Ejecutar: & "$env:TEMP\codetrace-cpp.exe"

#include <iostream>
#include <numeric>
int main() {
    std::cout << "MCD: " << std::gcd(48, 18) << '\n';
    return 0;
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\03-algoritmos\euclides\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   MCD: 6
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
