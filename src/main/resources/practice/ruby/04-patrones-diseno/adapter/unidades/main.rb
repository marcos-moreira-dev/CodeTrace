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

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\04-patrones-diseno\adapter\unidades\main.rb
# Salida esperada:
#   Celsius: 25.0
# CodeTrace | FIN EJEMPLO DE CONSOLA
