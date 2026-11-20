# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente Deque: una misma secuencia implementa ambas puntas.
#
# Ruby Array#shift/unshift puede desplazar elementos O(n);
# la implementación de nodos doblemente enlazados aquí
# garantiza O(1) en cada operación de extremo.
require_relative 'deque'

def main
  d = DequeCurso.new
  d.agregar_primero('B'); d.agregar_primero('A'); d.agregar_ultimo('C')
  salida = [d.quitar_primero, d.quitar_ultimo, d.quitar_ultimo]
  raise 'Deque' unless salida == %w[A C B]
  p salida
end
main if __FILE__ == $PROGRAM_NAME

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\02-estructuras-datos\deque\main.rb
# Salida esperada:
#   ["A", "C", "B"]
# CodeTrace | FIN EJEMPLO DE CONSOLA
