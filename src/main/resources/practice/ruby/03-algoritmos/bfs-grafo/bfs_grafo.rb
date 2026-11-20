# CodeTrace | Referencia: Ruby 3.3
# Comprobar: ruby -c ".\03-algoritmos\bfs-grafo\bfs_grafo.rb"
# Ejecutar: ruby ".\03-algoritmos\bfs-grafo\bfs_grafo.rb"

# Cliente de BFS en Ruby con Hash y Array.
#
# Un arreglo actúa como cola usando un índice de lectura, sin shift.
# El conjunto de visitados es un Hash y evita ciclos del grafo.
# Para listas de adyacencia: O(V+E) tiempo y O(V) memoria.
def bfs(grafo, origen)
  raise ArgumentError, "origen" unless grafo.key?(origen)
  cola = [origen]
  vistos = { origen => true }
  salida = []
  indice = 0
  while indice < cola.length
    actual = cola[indice]
    indice += 1
    salida << actual
    (grafo[actual] || []).each do |vecino|
      next if vistos[vecino]
      vistos[vecino] = true
      cola << vecino
    end
  end
  salida
end
grafo = { "A" => %w[B C], "B" => ["D"], "C" => ["E"], "D" => [], "E" => [] }
p bfs(grafo, "A")

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\03-algoritmos\bfs-grafo\bfs_grafo.rb
# Salida esperada:
#   ["A", "B", "C", "D", "E"]
# CodeTrace | FIN EJEMPLO DE CONSOLA
