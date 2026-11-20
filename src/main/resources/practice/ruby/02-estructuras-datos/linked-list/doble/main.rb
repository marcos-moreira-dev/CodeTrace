# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente Linked List doble: dos direcciones y retiro en O(1).
#
# La lista simple no guarda anterior, por lo que llegar al
# penúltimo exige recorrer. La doble mantiene la referencia
# adicional para extraer y recorrer desde el extremo final.
require_relative 'lista'

def main
  l = ListaDoble.new
  %w[A B C].each { |x| l.agregar_final(x) }
  raise 'Direcciones' unless l.valores == %w[A B C] && l.valores(true) == %w[C B A]
  raise 'Retiro' unless l.quitar_final == 'C' && l.cantidad == 2
  p l.valores
end
main if __FILE__ == $PROGRAM_NAME
