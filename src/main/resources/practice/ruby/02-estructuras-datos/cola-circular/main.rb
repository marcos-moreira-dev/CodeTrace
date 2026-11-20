# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde esta carpeta: ruby main.rb
# Archivos auxiliares: require_relative, sin gemas ni red externas.

# Cliente de cola circular, probando wrap-around de un arreglo fijo.
#
# A diferencia de Array#shift, la implementación no mueve elementos:
# solo cambia índices, coste O(1). Mantiene cantidad y detecta lleno.
require_relative 'cola'

def main
  cola = ColaCircular.new(3)
  [10, 20, 30].each { |x| cola.encolar(x) }
  raise 'FIFO' unless cola.desencolar == 10
  cola.encolar(40)
  resultado = Array.new(3) { cola.desencolar }
  raise 'FIFO circular' unless resultado == [20, 30, 40]
  p resultado
end

main if __FILE__ == $PROGRAM_NAME
