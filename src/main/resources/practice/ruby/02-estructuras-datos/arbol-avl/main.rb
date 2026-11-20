# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente AVL: insertar ascendentemente demuestra las rotaciones.
#
# Un BST simple terminaría con altura 15. Este AVL mantiene
# factor de balance <= 1 y altura O(log n). La comprobación
# valida cada subárbol, no sólo la altura de la raíz.
require_relative 'avl'

def main
  arbol = ArbolAVL.new
  (1..15).each { |x| arbol.insertar(x) }
  raise 'Inorden' unless arbol.inorden == (1..15).to_a
  raise 'Altura' unless arbol.validar <= 5
  puts "Altura AVL: #{arbol.validar}"
end
main if __FILE__ == $PROGRAM_NAME

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\02-estructuras-datos\arbol-avl\main.rb
# Salida esperada:
#   Altura AVL: 4
# CodeTrace | FIN EJEMPLO DE CONSOLA
