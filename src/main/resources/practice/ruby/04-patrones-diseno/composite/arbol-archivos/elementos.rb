# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c elementos.rb
# Ejecutar desde el cliente: ruby main.rb

# Composite: hojas y carpetas exponen el mismo método bytes.
class Archivo
  def initialize(nombre, tam)
    raise ArgumentError, 'Tamaño' if tam.negative?
    @nombre = nombre
    @tam = tam
  end
  def bytes; @tam; end
end

class Carpeta
  def initialize(nombre)
    @nombre = nombre
    @hijos = []
  end
  def agregar(elemento)
    raise ArgumentError, 'Ciclo directo' if elemento.equal?(self)
    @hijos << elemento
  end
  def bytes
    @hijos.sum(&:bytes)
  end
end
