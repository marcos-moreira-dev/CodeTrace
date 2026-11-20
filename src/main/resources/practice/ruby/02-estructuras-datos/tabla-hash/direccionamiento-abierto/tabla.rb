# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c tabla.rb
# Ejecutar desde el cliente: ruby main.rb

# Hash por sondeo lineal de capacidad fija. Sin eliminación ni resize.
# La eliminación necesitaría tombstones para preservar cadenas de sondeo.
class HashAbierto
  attr_reader :cantidad
  def initialize(capacidad)
    raise ArgumentError, 'Capacidad' unless capacidad.positive?
    @a = Array.new(capacidad)
    @cantidad = 0
  end
  def poner(clave, valor)
    inicio = clave.hash % @a.length
    @a.length.times do |paso|
      i = (inicio+paso) % @a.length
      par = @a[i]
      if par.nil?
        @a[i] = [clave, valor]
        @cantidad += 1
        return
      end
      if par[0] == clave
        @a[i] = [clave, valor]
        return
      end
    end
    raise IndexError, 'Tabla llena'
  end
  def buscar(clave)
    inicio = clave.hash % @a.length
    @a.length.times do |paso|
      par = @a[(inicio+paso) % @a.length]
      return nil if par.nil?
      return par[1] if par[0] == clave
    end
    nil
  end
end
