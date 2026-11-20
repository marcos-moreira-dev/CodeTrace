# CodeTrace | Referencia: Ruby 3+, Observer
# Compilar: ruby 04-patrones-diseno/observer/main.rb
class CentroEventos
  def initialize
    @observadores = []
  end
  def registrar(&bloque)
    @observadores << bloque
  end
  def publicar(evento)
    @observadores.each { |o| o.call(evento) }
  end
end
