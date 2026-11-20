# CodeTrace | Referencia: Ruby 3.3.
# Comandos desde la carpeta del paquete, en una COPIA:
# Comprobar sintaxis: ruby -c '.\01-fundamentos/objetos\contador.rb'
# Ejecutar: ruby '.\01-fundamentos/objetos\contador.rb'

class Contador
  def initialize
    @valor = 0
  end

  def incrementar
    @valor += 1
  end
end

puts Contador.new.incrementar

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\01-fundamentos\objetos\contador.rb
# Salida esperada:
#   1
# CodeTrace | FIN EJEMPLO DE CONSOLA
