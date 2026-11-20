// CodeTrace | Referencia: C++20 / g++ 13+
// Compilar desde copia (PowerShell): g++ -std=c++20 -Wall -Wextra ".\03-algoritmos\mochila_01.cpp" -o "$env:TEMP\ct-cpp-ejemplo.exe"
// Ejecutar: & "$env:TEMP\ct-cpp-ejemplo.exe"

#include <algorithm>
#include <iostream>
#include <stdexcept>
#include <vector>

/**
 * Cliente de mochila 0/1 mediante una tabla compacta de DP.
 *
 * Los artículos no se repiten: la capacidad se recorre hacia
 * atrás en cada artículo. Con n artículos y capacidad C,
 * O(n*C) tiempo y O(C) memoria, seudopolinómico respecto a C.
 */
int maximo(const std::vector<int>& peso, const std::vector<int>& valor, int C) {
    if (peso.size() != valor.size() || C < 0)
        throw std::invalid_argument("Entradas");
    std::vector<int> dp(static_cast<size_t>(C) + 1);
    for (size_t i = 0; i < peso.size(); i++) {
        if (peso[i] <= 0 || valor[i] < 0) throw std::invalid_argument("Artículo");
        for (int c = C; c >= peso[i]; --c)
            dp[c] = std::max(dp[c], dp[c - peso[i]] + valor[i]);
    }
    return dp[C];
}
int main() {
    std::cout << maximo({2, 3, 4, 5}, {3, 4, 5, 8}, 8) << '\n';
}
