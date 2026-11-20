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

# Salida esperada de consola:
#   Altura AVL: #{arbol.validar}
#   (los valores variables completan los fragmentos anteriores)
# Verificacion: termina sin excepciones inesperadas.
