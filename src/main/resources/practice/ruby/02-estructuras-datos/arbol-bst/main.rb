# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde esta carpeta: ruby main.rb
# Archivos auxiliares: require_relative, sin gemas ni red externas.

# Cliente BST: insertar y recorrer en orden ascendente.
#
# Invariante: todo valor menor queda a la izquierda. Una entrada
# ya ordenada convierte este árbol en una lista, peor caso O(n)
# para buscar/insertar. AVL exige rotaciones y logra O(log n).
require_relative 'arbol_bst'

def main
  arbol = Arbol.new
  [8, 3, 10, 1, 6, 4].each { |x| arbol.insertar(x) }
  esperado = [1, 3, 4, 6, 8, 10]
  raise 'BST incorrecto' unless arbol.inorden == esperado
  p arbol.inorden
end

main if __FILE__ == $PROGRAM_NAME

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\02-estructuras-datos\arbol-bst\main.rb
# Salida esperada:
#   [1, 3, 4, 6, 8, 10]
# CodeTrace | FIN EJEMPLO DE CONSOLA
