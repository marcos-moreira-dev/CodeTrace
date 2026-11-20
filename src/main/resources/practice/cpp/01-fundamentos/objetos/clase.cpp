// CodeTrace | Referencia: C++20; g++ 13+.
// Comandos desde PowerShell, en una COPIA de la carpeta del paquete:
// Compilar/verificar: g++ -std=c++20 -Wall -Wextra '.\01-fundamentos/objetos\clase.cpp' -o "$env:TEMP\codetrace-cpp.exe"
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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\01-fundamentos\objetos\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   1
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
