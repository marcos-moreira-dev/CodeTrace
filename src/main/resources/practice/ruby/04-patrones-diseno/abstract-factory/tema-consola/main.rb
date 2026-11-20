# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente Abstract Factory: un tema completo con botón y diálogo.
#
# A diferencia de Factory Method, la fábrica configura FAMILIAS
# de productos, no un único método de creación. Se usan cadenas
# para demostrar contratos de consola sin framework visual.
require_relative 'familias'

def pantalla(f)
  "#{f.boton.pintar} / #{f.dialogo.pintar}"
end

def main
  a = pantalla(FabricaClara.new)
  b = pantalla(FabricaOscura.new)
  raise 'Familias' if a == b || !a.include?('clara') || !b.include?('oscura')
  puts a, b
end
main if __FILE__ == $PROGRAM_NAME

# Salida esperada de consola:
#   Se imprime el valor o coleccion calculado por el cliente.
# Verificacion: termina sin excepciones inesperadas.
