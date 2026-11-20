# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente heap máximo: el arreglo no está completamente ordenado.
#
# Un heap garantiza la relación padre>=hijos, no el orden global
# de una lista. El máximo se extrae en O(log n) restaurando
# la relación de heap con los nodos restantes.
require_relative 'heap'

def main
  heap = HeapMaximo.new
  [4,1,9,7,2].each { |n| heap.insertar(n) }
  raise 'Heap' unless Array.new(5) { heap.extraer } == [9,7,4,2,1]
  puts 'Max heap correcto'
end
main if __FILE__ == $PROGRAM_NAME

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\02-estructuras-datos\heap-maximo\main.rb
# Salida esperada:
#   Max heap correcto
# CodeTrace | FIN EJEMPLO DE CONSOLA
