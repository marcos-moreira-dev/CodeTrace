# CodeTrace | Referencia: Ruby 3+, Observer
# Compilar: ruby 04-patrones-diseno/observer/main.rb
require_relative "centro_eventos"
centro = CentroEventos.new
centro.registrar { |evento| puts "Evento: #{evento}" }
centro.publicar("Factura 001")

# Salida esperada de consola:
#   Se imprime el valor o coleccion calculado por el cliente.
# Verificacion: termina sin excepciones inesperadas.
