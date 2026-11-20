# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente Composite: archivos y carpetas se consultan uniformemente.
#
# La operación bytes recorre O(n) nodos por su naturaleza recursiva.
# En Ruby duck typing permite que ambos tipos respondan a bytes
# sin heredar obligatoriamente de una misma clase abstracta.
require_relative 'elementos'

def main
  raiz = Carpeta.new('proyecto')
  src = Carpeta.new('src')
  src.agregar(Archivo.new('Main.rb',120))
  src.agregar(Archivo.new('App.rb',80))
  raiz.agregar(src)
  raiz.agregar(Archivo.new('README.md',20))
  raise 'Composite' unless raiz.bytes == 220
  puts "Bytes: #{raiz.bytes}"
end
main if __FILE__ == $PROGRAM_NAME

# Salida esperada de consola:
#   Bytes: #{raiz.bytes}
#   (los valores variables completan los fragmentos anteriores)
# Verificacion: termina sin excepciones inesperadas.
