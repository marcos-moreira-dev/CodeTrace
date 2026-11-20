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
