# CodeTrace | Referencia: Ruby 3+, Observer
# Compilar: ruby 07-proyectos/observer/main.rb
require_relative "centro_eventos"
centro = CentroEventos.new
centro.registrar { |evento| puts "Evento: #{evento}" }
centro.publicar("Factura 001")
