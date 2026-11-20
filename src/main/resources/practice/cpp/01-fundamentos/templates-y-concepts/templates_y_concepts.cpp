// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar desde copia (PowerShell): g++ -std=c++20 -Wall -Wextra ".\01-fundamentos\templates-y-concepts\templates_y_concepts.cpp" -o "$env:TEMP\ct-cpp-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-cpp-ejemplo.exe"

#include <concepts>
#include <iostream>
#include <vector>

/**
 * Cliente de templates y concepts introducidos en C++20.
 *
 * Un template describe operaciones para una familia de tipos. El
 * concepto std::integral restringe instanciaciones a enteros, haciendo
 * el contrato más comprensible y los errores más cercanos al uso.
 * Un vector<T> almacena elementos contiguos con tipo conocido.
 */
template<std::integral T>
T sumar(const std::vector<T>& valores) {
    T resultado{};
    for (T x : valores) resultado += x;
    return resultado;
}

int main() {
    const std::vector<int> enteros{1, 2, 3, 4};
    std::cout << "Suma: " << sumar(enteros) << '\n';
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\01-fundamentos\templates-y-concepts\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   Suma: 10
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
