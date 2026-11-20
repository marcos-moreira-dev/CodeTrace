# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente FIFO enlazado frente a un buffer de capacidad fija.
#
# La cola enlazada crece por nodos y no reserva un arreglo
# completo, pero añade memoria para referencias. Reiniciar
# tail al vaciar y después volver a llenar es un caso límite.
require_relative 'cola'

def main
  q = ColaEnlazada.new
  q.encolar('A'); q.encolar('B')
  raise 'FIFO' unless q.desencolar == 'A' && q.desencolar == 'B'
  q.encolar('C')
  raise 'Tail' unless q.desencolar == 'C' && q.cantidad.zero?
  puts 'Cola enlazada correcta'
end
main if __FILE__ == $PROGRAM_NAME
