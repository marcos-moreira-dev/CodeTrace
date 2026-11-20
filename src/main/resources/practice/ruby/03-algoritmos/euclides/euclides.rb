# CodeTrace | Referencia: Ruby 3.3.
# Desde PowerShell en una COPIA del paquete:
# Comprobar sintaxis: ruby -c '.\03-algoritmos\euclides\euclides.rb'
# Ejecutar: ruby '.\03-algoritmos\euclides\euclides.rb'

def mcd(a, b)
  until b.zero?
    a, b = b, a % b
  end
  a
end

puts "MCD: #{mcd(48, 18)}"

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\03-algoritmos\euclides\euclides.rb
# Salida esperada:
#   MCD: 6
# CodeTrace | FIN EJEMPLO DE CONSOLA
