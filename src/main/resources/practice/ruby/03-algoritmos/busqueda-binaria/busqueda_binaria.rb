# CodeTrace | Referencia: Ruby 3.3
# Compilar/Comprobar: ruby -c 03-algoritmos/busqueda-binaria/busqueda_binaria.rb
# Ejecutar: ruby 03-algoritmos/busqueda-binaria/busqueda_binaria.rb

# Cliente de búsqueda binaria sobre un arreglo ordenado.
#
# Presupone orden ascendente y mantiene un rango [inicio, fin).
# Descartar mitades requiere O(log n) tiempo y O(1) espacio.
def buscar(arreglo, objetivo)
  inicio = 0
  fin = arreglo.length
  while inicio < fin
    mitad = inicio + (fin - inicio) / 2
    return mitad if arreglo[mitad] == objetivo
    if arreglo[mitad] < objetivo
      inicio = mitad + 1
    else
      fin = mitad
    end
  end
  -1
end
puts buscar([1, 2, 5, 8, 13], 8)
