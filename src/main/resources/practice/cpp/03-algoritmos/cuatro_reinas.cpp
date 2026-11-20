// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar desde copia (PowerShell): g++ -std=c++20 -Wall -Wextra ".\03-algoritmos\cuatro_reinas.cpp" -o "$env:TEMP\ct-cpp-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-cpp-ejemplo.exe"

#include <cmath>
#include <iostream>
#include <vector>

/**
 * Cliente de backtracking: cuatro reinas sin conflictos.
 *
 * Para cada fila se elige columna y se descartan las que estén
 * atacadas por reinas previas. Si la rama es válida se recurre.
 * El árbol de búsqueda es combinatorio; la poda mejora casos,
 * pero no convierte el problema general en uno de tiempo lineal.
 */
void resolver(int fila, std::vector<int>& columnas, int& soluciones) {
    if (fila == static_cast<int>(columnas.size())) {
        ++soluciones;
        for (int c : columnas) std::cout << c << ' ';
        std::cout << '\n';
        return;
    }
    for (int col = 0; col < static_cast<int>(columnas.size()); col++) {
        bool valida = true;
        for (int f = 0; f < fila; f++)
            if (columnas[f] == col || std::abs(columnas[f] - col) == fila - f)
                valida = false;
        if (valida) { columnas[fila] = col; resolver(fila + 1, columnas, soluciones); }
    }
}
int main() {
    std::vector<int> columnas(4);
    int soluciones = 0;
    resolver(0, columnas, soluciones);
    std::cout << "Soluciones: " << soluciones << '\n';
}
