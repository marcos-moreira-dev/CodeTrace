// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar desde copia (PowerShell): g++ -std=c++20 -Wall -Wextra ".\03-algoritmos\merge_sort.cpp" -o "$env:TEMP\ct-cpp-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-cpp-ejemplo.exe"

#include <iostream>
#include <vector>

/**
 * Cliente de Merge Sort: dividir para vencer y combinar.
 *
 * Dos mitades ordenadas pueden fusionarse en tiempo lineal.
 * La profundidad de divisiones es O(log n), por lo que el
 * tiempo total es O(n log n). Se reserva O(n) memoria auxiliar.
 */
void ordenar(std::vector<int>& a, std::vector<int>& aux, int lo, int hi) {
    if (hi - lo < 2) return;
    int mid = lo + (hi - lo) / 2;
    ordenar(a, aux, lo, mid);
    ordenar(a, aux, mid, hi);
    int i = lo, j = mid;
    for (int k = lo; k < hi; ++k)
        aux[k] = (j == hi || (i < mid && a[i] <= a[j])) ? a[i++] : a[j++];
    for (int k = lo; k < hi; ++k) a[k] = aux[k];
}
int main() {
    std::vector<int> datos{9, 1, 6, 2, 8, 4};
    std::vector<int> auxiliar(datos.size());
    ordenar(datos, auxiliar, 0, static_cast<int>(datos.size()));
    for (int x : datos) std::cout << x << ' ';
    std::cout << '\n';
}
