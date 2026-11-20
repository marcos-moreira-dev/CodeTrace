# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c solicitud.rb
# Ejecutar desde el cliente: ruby main.rb

# Builder construye un producto de forma incremental y validada.
# Ruby Data.define (Ruby 3.2+) representa un registro por valor.
Solicitud = Data.define(:ruta, :metodo, :cabeceras)

class SolicitudBuilder
  def initialize
    @ruta = ''
    @metodo = 'GET'
    @cabeceras = []
  end
  def ruta(valor); @ruta = valor; self; end
  def metodo(valor); @metodo = valor; self; end
  def cabecera(valor); @cabeceras << valor; self; end
  def construir
    raise ArgumentError, 'Solicitud' unless @ruta.start_with?('/') && !@metodo.empty?
    Solicitud.new(@ruta, @metodo, @cabeceras.dup.freeze)
  end
end

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\04-patrones-diseno\builder\solicitud\main.rb
# Nota: solicitud.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   POST /facturas (1 cabecera)
# CodeTrace | FIN EJEMPLO DE CONSOLA
