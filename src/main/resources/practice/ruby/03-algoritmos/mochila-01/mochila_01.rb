# CodeTrace | Referencia: Ruby 3.3
# Comprobar: ruby -c ".\03-algoritmos\mochila-01\mochila_01.rb"
# Ejecutar: ruby ".\03-algoritmos\mochila-01\mochila_01.rb"

# Cliente de programación dinámica para mochila 0/1.
#
# La capacidad se recorre en orden descendente para impedir que
# un artículo participe varias veces. Tiempo O(n*C), memoria O(C).
# La complejidad es seudopolinómica en la capacidad C.
def mochila(pesos, valores, capacidad)
  raise ArgumentError, "entrada" unless pesos.length == valores.length && capacidad >= 0
  tabla = Array.new(capacidad + 1, 0)
  pesos.each_with_index do |peso, indice|
    valor = valores[indice]
    raise ArgumentError, "artículo" unless peso > 0 && valor >= 0
    capacidad.downto(peso) do |c|
      tabla[c] = [tabla[c], tabla[c - peso] + valor].max
    end
  end
  tabla[capacidad]
end
puts mochila([2, 3, 4, 5], [3, 4, 5, 8], 8)

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\03-algoritmos\mochila-01\mochila_01.rb
# Salida esperada:
#   12
# CodeTrace | FIN EJEMPLO DE CONSOLA
