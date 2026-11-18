// CodeTrace | Referencia: C++20, g++ 13+.
// Desde PowerShell en una COPIA del paquete:
// Compilar: g++ -std=c++20 -Wall -Wextra '.\04-estructuras\Pila.cpp' -o "$env:TEMP\codetrace-cpp.exe"
// Ejecutar: & "$env:TEMP\codetrace-cpp.exe"

#include <iostream>
#include <stack>
int main() {
    std::stack<int> pila;
    pila.push(12); pila.push(8);
    std::cout << pila.top() << '\n';
    pila.pop();
    return 0;
}
