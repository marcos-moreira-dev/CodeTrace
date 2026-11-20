// CodeTrace | Referencia: C++20 / g++ 13+ / STL
// Compilar en COPIA desde la carpeta: g++ -std=c++20 -Wall -Wextra -Werror main.cpp -o ejemplo
// Ejecutar: ./ejemplo en Linux/WSL (ejemplo.exe en Windows)
// Para unidades con .cpp de implementación: compilar main.cpp + *.cpp.

#pragma once
#include <functional>
#include <optional>
#include <stdexcept>
#include <string>
#include <utility>
#include <vector>

/**
 * Hash por SONDEO LINEAL: capacidad fija, sin borrado ni resize.
 * Expected O(1) baja carga, peor O(n), distinto de unordered_map
 * de producción, que suele usar otra estrategia de colisiones.
 */
class HashAbierto{
    using Entrada=std::pair<std::string,int>;
    std::vector<std::optional<Entrada>> tabla;
    int cantidad_=0;
    std::size_t indice(const std::string& k)const{
        return std::hash<std::string>{}(k)%tabla.size();
    }
public:
    explicit HashAbierto(std::size_t n):tabla(n){
        if(n==0)throw std::invalid_argument("Capacidad");
    }
    void poner(const std::string& k,int v){
        auto ini=indice(k);
        for(std::size_t paso=0;paso<tabla.size();paso++){
            auto& slot=tabla[(ini+paso)%tabla.size()];
            if(!slot){slot=Entrada(k,v);cantidad_++;return;}
            if(slot->first==k){slot->second=v;return;}
        }
        throw std::overflow_error("Tabla llena");
    }
    std::optional<int> buscar(const std::string& k)const{
        auto ini=indice(k);
        for(std::size_t paso=0;paso<tabla.size();paso++){
            const auto& slot=tabla[(ini+paso)%tabla.size()];
            if(!slot)return std::nullopt;
            if(slot->first==k)return slot->second;
        }
        return std::nullopt;
    }
    int cantidad()const{return cantidad_;}
};

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   g++ -std=c++20 -Wall -Wextra .\02-estructuras-datos\tabla-hash\direccionamiento-abierto\*.cpp -o "$env:TEMP\ct-ejemplo-cpp.exe"
 *   & "$env:TEMP\ct-ejemplo-cpp.exe"
 * Nota: HashAbierto.hpp es un componente; la salida corresponde al cliente main.cpp.
 * Salida esperada:
 *   Tabla llena controlada
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
