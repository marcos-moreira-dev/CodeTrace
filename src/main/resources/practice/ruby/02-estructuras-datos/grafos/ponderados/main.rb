# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente grafo ponderado: aristas dirigidas y latencias.
#
# La estructura almacena pesos, pero NO decide qué algoritmo
# los admite. Dijkstra exige no negativos, Bellman-Ford tiene
# otra condición sobre ciclos negativos alcanzables.
require_relative 'grafo'

def main
  g = GrafoPonderado.new
  g.conectar('GYE','UIO',12.0)
  g.conectar('GYE','CUE',18.0)
  g.conectar('UIO','CUE',8.0)
  raise 'Dirección' unless g.vecinos('GYE').length == 2 &&
    g.vecinos('CUE').empty?
  p g.vecinos('GYE')
end
main if __FILE__ == $PROGRAM_NAME

# Salida esperada de consola:
#   Se imprime el valor o coleccion calculado por el cliente.
# Verificacion: termina sin excepciones inesperadas.
