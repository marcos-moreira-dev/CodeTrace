# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde esta carpeta: ruby main.rb
# Archivos auxiliares: require_relative, sin gemas ni red externas.

# Cliente Adapter: una interfaz externa expresa temperatura Fahrenheit,
# pero el dominio necesita Celsius. El adaptador encapsula la traducción.
#
# Ruby usa duck typing: el cliente sólo necesita que el objeto entienda
# celsius. No obliga a definir una interfaz abstracta como Java/C#.
require_relative 'sensores'

def main
  sensor = AdaptadorCelsius.new(TermometroLegado.new)
  raise 'Conversión incorrecta' unless sensor.celsius == 25.0
  puts "Celsius: #{sensor.celsius}"
end

main if __FILE__ == $PROGRAM_NAME

# Salida esperada de consola:
#   Celsius: #{sensor.celsius}
#   (los valores variables completan los fragmentos anteriores)
# Verificacion: termina sin excepciones inesperadas.
