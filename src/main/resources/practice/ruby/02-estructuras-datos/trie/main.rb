# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente Trie: los prefijos compartidos reducen trabajo repetido.
#
# Un prefijo existente puede NO representar una palabra completa.
# El diccionario de Ruby usa caracteres, mientras C++ del curso
# toma bytes; esa diferencia es propia del manejo de Unicode.
require_relative 'trie'

def main
  trie = Trie.new
  trie.insertar('casa'); trie.insertar('casco')
  raise 'Prefijo' unless trie.prefijo('cas') && !trie.contiene('cas')
  raise 'Palabra' unless trie.contiene('casa') && !trie.contiene('cama')
  puts 'Trie correcto'
end
main if __FILE__ == $PROGRAM_NAME

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\02-estructuras-datos\trie\main.rb
# Salida esperada:
#   Trie correcto
# CodeTrace | FIN EJEMPLO DE CONSOLA
