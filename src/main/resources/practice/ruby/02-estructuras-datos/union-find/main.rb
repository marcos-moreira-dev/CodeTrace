# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente Union-Find: conectividad transitiva usada por Kruskal.
#
# Si 0-1 y 1-2 ya están conectados, intentar unir 0-2
# no cambia el número de componentes. Union-Find no reconstruye
# una secuencia de nodos para recorrer entre ellos.
require_relative 'union_find'

def main
  uf = UnionFind.new(5)
  raise 'Uniones' unless uf.unir(0,1) && uf.unir(1,2) && !uf.unir(0,2)
  raise 'Componentes' unless uf.conectados(0,2) &&
    !uf.conectados(0,4) && uf.componentes == 3
  puts "Componentes: #{uf.componentes}"
end
main if __FILE__ == $PROGRAM_NAME

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\02-estructuras-datos\union-find\main.rb
# Salida esperada:
#   Componentes: 3
# CodeTrace | FIN EJEMPLO DE CONSOLA
