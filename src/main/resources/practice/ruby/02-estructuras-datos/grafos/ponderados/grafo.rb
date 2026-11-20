# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c grafo.rb
# Ejecutar desde el cliente: ruby main.rb

# Grafo dirigido con pesos; memoria O(V+E) en listas de adyacencia.
class GrafoPonderado
  Arista = Struct.new(:destino, :peso)
  def initialize
    @ady = Hash.new { |h,k| h[k] = [] }
  end
  def conectar(origen, destino, peso)
    raise ArgumentError, 'Peso finito' unless peso.finite?
    @ady[origen] << Arista.new(destino, peso)
    @ady[destino]
  end
  def vecinos(origen)
    @ady.fetch(origen).dup
  end
end

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\02-estructuras-datos\grafos\ponderados\main.rb
# Nota: grafo.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   [#<struct GrafoPonderado::Arista destino="UIO", peso=12.0>, #<struct GrafoPonderado::Arista destino="CUE", peso=18.0>]
# CodeTrace | FIN EJEMPLO DE CONSOLA
