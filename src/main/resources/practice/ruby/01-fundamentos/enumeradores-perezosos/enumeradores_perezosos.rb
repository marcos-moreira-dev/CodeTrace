# CodeTrace | Referencia: Ruby 3.3
# Comprobar: ruby -c ".\01-fundamentos\enumeradores-perezosos\enumeradores_perezosos.rb"
# Ejecutar: ruby ".\01-fundamentos\enumeradores-perezosos\enumeradores_perezosos.rb"

# Cliente de Enumerator::Lazy y evaluación bajo demanda.
#
# Una cadena lazy genera datos a medida que se consumen, a diferencia
# de map que materializa de inmediato todos los resultados. first(5)
# limita un iterador potencialmente infinito sin recorrerlo entero.
fibonacci = Enumerator.new do |salida|
  a = 0
  b = 1
  loop do
    salida << a
    a, b = b, a + b
  end
end
p fibonacci.lazy.select(&:even?).first(5)
