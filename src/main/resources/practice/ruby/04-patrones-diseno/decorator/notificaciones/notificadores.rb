# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde esta carpeta: ruby main.rb
# Archivos auxiliares: require_relative, sin gemas ni red externas.

# Decorator GoF: un objeto envuelve otro con el MISMO contrato.
# No confundir con Ruby Module#prepend, que sirve para otro estilo.
class Consola
  def enviar(texto)
    puts texto
  end
end

class ConPrefijo
  def initialize(interno, etiqueta)
    @interno = interno
    @etiqueta = etiqueta
  end
  def enviar(texto)
    @interno.enviar(@etiqueta + texto)
  end
end

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\04-patrones-diseno\decorator\notificaciones\main.rb
# Nota: notificadores.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   [INFO] Pedido confirmado
# CodeTrace | FIN EJEMPLO DE CONSOLA
