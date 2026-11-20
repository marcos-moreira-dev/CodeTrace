# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente Linked List doble circular, variante combinada.
#
# El ejemplo muestra que la dirección de recorrido puede
# invertirse sin que desaparezca la naturaleza circular.
# Cada nodo mantiene dos enlaces: más memoria por elemento.
require_relative 'lista'

def main
  l = ListaDobleCircular.new
  [2, 3, 5].each { |x| l.agregar(x) }
  raise 'Adelante' unless l.valores == [2, 3, 5]
  raise 'Atrás' unless l.valores(true) == [5, 3, 2]
  p l.valores
end
main if __FILE__ == $PROGRAM_NAME
