# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente Command: historial de peticiones reversibles.
#
# El objeto Comando conserva estado y comportamiento; no es solo
# un callback. Editor representa al receptor. El invocador mantiene
# una pila LIFO y llama a deshacer sobre el comando más reciente.
require_relative 'comandos'

def main
  editor = Editor.new
  historial = []
  ['Hola', ' mundo'].each do |parte|
    comando = AgregarTexto.new(editor, parte)
    comando.ejecutar
    historial << comando
  end
  raise 'Ejecutar' unless editor.texto == 'Hola mundo'
  historial.pop.deshacer
  raise 'Deshacer' unless editor.texto == 'Hola'
  puts editor.texto
end
main if __FILE__ == $PROGRAM_NAME

# Salida esperada de consola:
#   Hola
# El último comando se deshace en orden LIFO.
