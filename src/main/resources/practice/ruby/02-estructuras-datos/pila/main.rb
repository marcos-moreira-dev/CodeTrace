# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde esta carpeta: ruby main.rb
# Archivos auxiliares: require_relative, sin gemas ni red externas.

# Cliente de Pila LIFO: la última inserción sale primero.
#
# Array implementa eficientemente la pila; no es preciso crear nodos
# por herencia. Se valida orden LIFO y el rechazo del caso vacío.
require_relative 'pila'

def main
  pila = Pila.new
  %w[A B C].each { |x| pila.apilar(x) }
  raise 'LIFO incorrecto' unless pila.desapilar == 'C' && pila.desapilar == 'B'
  raise 'Cantidad incorrecta' unless pila.cantidad == 1
  puts 'Pila correcta'
end

main if __FILE__ == $PROGRAM_NAME
