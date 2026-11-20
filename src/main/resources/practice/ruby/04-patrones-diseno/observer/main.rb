# CodeTrace | Referencia: Ruby 3+, Observer
# Compilar: ruby 04-patrones-diseno/observer/main.rb
require_relative "centro_eventos"
centro = CentroEventos.new
centro.registrar { |evento| puts "Evento: #{evento}" }
centro.publicar("Factura 001")

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\04-patrones-diseno\observer\main.rb
# Salida esperada:
#   Evento: Factura 001
# CodeTrace | FIN EJEMPLO DE CONSOLA
