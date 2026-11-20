# CodeTrace | Referencia: Ruby 3.3.
# Desde PowerShell en una COPIA del paquete:
# Comprobar sintaxis: ruby -c '.\03-algoritmos\euclides.rb'
# Ejecutar: ruby '.\03-algoritmos\euclides.rb'

def mcd(a, b)
  until b.zero?
    a, b = b, a % b
  end
  a
end

puts "MCD: #{mcd(48, 18)}"
