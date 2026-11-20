// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar desde copia (PowerShell): g++ -std=c++20 -Wall -Wextra ".\01-fundamentos\span-y-iteradores\span_y_iteradores.cpp" -o "$env:TEMP\ct-cpp-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-cpp-ejemplo.exe"

#include <algorithm>
#include <iostream>
#include <span>
#include <vector>

/**
 * Cliente de std::span: una vista no propietaria sobre memoria contigua.
 *
 * Un span permite recibir un arreglo nativo o vector sin copiarlo.
 * Su vida útil depende de que el almacenamiento siga existiendo.
 * std::ranges::count_if usa un predicado para recorrer elementos.
 */
int contar_pares(std::span<const int> numeros) {
    return static_cast<int>(std::ranges::count_if(numeros,
        [](int x) { return x % 2 == 0; }));
}
int main() {
    int arreglo[] = {1, 2, 3, 4, 5, 6};
    std::vector<int> vector{7, 8, 9, 10};
    std::cout << contar_pares(arreglo) << " y " << contar_pares(vector) << '\n';
}
