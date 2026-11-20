# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde esta carpeta: ruby main.rb
# Archivos auxiliares: require_relative, sin gemas ni red externas.

# Factory Method: un creador declara la fábrica; subclase decide el producto.
# El cliente no requiere construir objetos concretos con condicionales.
class ExportadorTexto
  def exportar(texto)
    "TXT: #{texto}"
  end
end

class Creador
  def preparar(texto)
    crear.exportar(texto)
  end
  private
  def crear
    raise NotImplementedError, 'La subclase decide el producto'
  end
end

class CreadorTexto < Creador
  private
  def crear
    ExportadorTexto.new
  end
end

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\04-patrones-diseno\factory-method\exportadores\main.rb
# Nota: fabrica.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   TXT: Factura 001
# CodeTrace | FIN EJEMPLO DE CONSOLA
