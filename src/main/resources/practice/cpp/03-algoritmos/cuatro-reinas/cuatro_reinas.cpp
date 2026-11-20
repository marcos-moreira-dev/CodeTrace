// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar desde copia (PowerShell): g++ -std=c++20 -Wall -Wextra ".\03-algoritmos\cuatro-reinas\cuatro_reinas.cpp" -o "$env:TEMP\ct-cpp-ejemplo.exe"
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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\03-algoritmos\cuatro-reinas\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Salida esperada:
 *   1 3 0 2 
 *   2 0 3 1 
 *   Soluciones: 2
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
