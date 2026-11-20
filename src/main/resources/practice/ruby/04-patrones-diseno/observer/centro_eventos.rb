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

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\04-patrones-diseno\observer\main.rb
# Nota: centro_eventos.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   Evento: Factura 001
# CodeTrace | FIN EJEMPLO DE CONSOLA
