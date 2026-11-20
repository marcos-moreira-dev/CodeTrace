# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente lista circular para turnos repetitivos.
#
# No se puede recorrer hasta nil: el anillo nunca alcanza nil.
# El conteo de elementos determina cuándo detener la iteración.
# La operación rotar solo mueve el último puntero, O(1).
require_relative 'lista'

def main
  l = ListaCircular.new
  raise 'Vacía' unless l.valores.empty?
  %w[Ana Luis Mia].each { |x| l.agregar(x) }
  l.rotar
  raise 'Circular' unless l.valores == %w[Luis Mia Ana]
  p l.valores
end
main if __FILE__ == $PROGRAM_NAME
