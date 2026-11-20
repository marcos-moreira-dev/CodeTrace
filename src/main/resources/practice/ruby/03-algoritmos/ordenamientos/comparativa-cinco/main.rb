# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c main.rb
# Ejecutar: ruby main.rb (desde esta carpeta)

# Cliente comparativa de cinco ordenamientos con entradas identicas.
#
# Comparar el mismo arreglo clonado, no un Array ya ordenado por un
# sort anterior. Este test no demuestra costo temporal o estabilidad;
# verifica funcionalidad, repetidos y entrada vacia.
require_relative 'ordenamientos'

def main
  metodos = {
    'Insercion' => Ordenamientos.method(:insercion),
    'Burbuja' => Ordenamientos.method(:burbuja),
    'Mezcla' => Ordenamientos.method(:mezcla),
    'HeapSort' => Ordenamientos.method(:heap),
    'QuickSort' => Ordenamientos.method(:rapido)
  }
  metodos.each do |nombre,fn|
    datos=[9,3,8,1,7,2,5]
    fn.call(datos)
    raise nombre unless datos==[1,2,3,5,7,8,9]
    puts "#{nombre}: #{datos.inspect}"
    vacio=[];repetidos=[3,1,3,1]
    fn.call(vacio);fn.call(repetidos)
    raise 'Limites' unless vacio.empty?&&repetidos==[1,1,3,3]
  end
end
main if __FILE__ == $PROGRAM_NAME

# Salida esperada (cinco lineas):
# Insercion: [1, 2, 3, 5, 7, 8, 9]
# Burbuja: [1, 2, 3, 5, 7, 8, 9]
# Mezcla: [1, 2, 3, 5, 7, 8, 9]
# HeapSort: [1, 2, 3, 5, 7, 8, 9]
# QuickSort: [1, 2, 3, 5, 7, 8, 9]

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\03-algoritmos\ordenamientos\comparativa-cinco\main.rb
# Salida esperada:
#   Insercion: [1, 2, 3, 5, 7, 8, 9]
#   Burbuja: [1, 2, 3, 5, 7, 8, 9]
#   Mezcla: [1, 2, 3, 5, 7, 8, 9]
#   HeapSort: [1, 2, 3, 5, 7, 8, 9]
#   QuickSort: [1, 2, 3, 5, 7, 8, 9]
# CodeTrace | FIN EJEMPLO DE CONSOLA
