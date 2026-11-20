# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde esta carpeta: ruby main.rb
# Archivos auxiliares: require_relative, sin gemas ni red externas.

# Observer ligero: se publican cambios a bloques registrados.
# Devolver un token permite desuscribir un callback para no retenerlo.
class Emisor
  def initialize
    @listeners = {}
    @siguiente = 0
  end

  def suscribir(&bloque)
    raise ArgumentError, 'Falta bloque' unless bloque
    @siguiente += 1
    @listeners[@siguiente] = bloque
    @siguiente
  end

  def desuscribir(token)
    @listeners.delete(token)
  end

  def actualizar(valor)
    @listeners.values.each { |callback| callback.call(valor) }
  end
end

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\04-patrones-diseno\observer\basico\main.rb
# Nota: observador.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   [42]
# CodeTrace | FIN EJEMPLO DE CONSOLA
