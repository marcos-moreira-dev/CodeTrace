# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA desde esta carpeta: ruby -c main.rb
# Ejecutar desde el cliente: ruby main.rb

# Cliente hash abierto: colisiones, actualización y tabla llena.
#
# Dos claves pueden tener el mismo índice inicial; el sondeo
# busca otra casilla, sin modificar el dato de la otra clave.
# Comparar con Hash estándar de Ruby (uso de producción).
require_relative 'tabla'

def main
  t = HashAbierto.new(2)
  t.poner('A',1);t.poner('B',2);t.poner('A',3)
  raise 'Hash' unless t.buscar('A') == 3 &&
    t.buscar('B') == 2 && t.buscar('X').nil? && t.cantidad == 2
  begin
    t.poner('C',4)
    raise 'Se aceptó tabla llena'
  rescue IndexError
    puts 'Tabla llena controlada'
  end
end
main if __FILE__ == $PROGRAM_NAME

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\02-estructuras-datos\tabla-hash\direccionamiento-abierto\main.rb
# Salida esperada:
#   Tabla llena controlada
# CodeTrace | FIN EJEMPLO DE CONSOLA
