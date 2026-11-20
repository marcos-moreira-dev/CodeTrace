# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente Linked List simple: nodos con referencia al siguiente.
#
# A diferencia de Array, esta lista no posee acceso aleatorio
# eficiente por índice. Insertar al principio O(1), recorrer O(n).
# El ejemplo de Ruby emplea Struct y su sistema dinámico de objetos.
require_relative 'lista'

def main
  l = ListaSimple.new
  %w[A B C].each { |x| l.agregar_primero(x) }
  raise 'Lista simple' unless l.valores == %w[C B A] && l.cantidad == 3
  p l.valores
end
main if __FILE__ == $PROGRAM_NAME
