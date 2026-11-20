# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c main.rb
# Ejecutar: ruby main.rb (desde esta carpeta)

# Cliente lower/upper bound para rango [1,4) de tres numeros 3.
# Buscar 4 devuelve posicion 4: no confundir con buscar y obtener -1.
require_relative 'limites'
def main
  a=[1,3,3,3,5,9]
  lo=LimitesOrdenados.lower(a,3)
  hi=LimitesOrdenados.upper(a,3)
  pos=LimitesOrdenados.lower(a,4)
  raise 'Limites' unless [lo,hi,pos,LimitesOrdenados.upper(a,4)]==[1,4,4,4]
  puts "Valor 3: [#{lo}, #{hi})"
  puts "Insertar 4 en indice: #{pos}"
end
main if __FILE__ == $PROGRAM_NAME
# Salida esperada:
# Valor 3: [1, 4)
# Insertar 4 en indice: 4
