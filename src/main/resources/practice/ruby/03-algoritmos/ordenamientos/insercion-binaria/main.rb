# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c main.rb
# Ejecutar: ruby main.rb (desde esta carpeta)

# Cliente de insercion binaria: buscar hueco con mitades no evita
# mover datos en Array. Comparar con Insertion Sort normal.
require_relative 'insercion_binaria'
def main
  datos=[5,1,3,3,2]
  InsercionBinaria.ordenar(datos)
  raise 'Orden' unless datos==[1,2,3,3,5]
  vacio=[];InsercionBinaria.ordenar(vacio)
  puts "Insercion binaria: #{datos.inspect}"
end
main if __FILE__ == $PROGRAM_NAME
# Salida esperada:
# Insercion binaria: [1, 2, 3, 3, 5]
