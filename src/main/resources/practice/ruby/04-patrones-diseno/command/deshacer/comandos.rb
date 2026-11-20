# CodeTrace | Referencia: Ruby 3.3
# Comprobar en COPIA: ruby -c comandos.rb
# Ejecutar desde el cliente: ruby main.rb

# Command: objetos que conocen ejecutar/deshacer y un receptor.
class Editor
  attr_accessor :texto
  def initialize; @texto = ''; end
  def agregar(parte); @texto += parte; end
end

class AgregarTexto
  def initialize(editor, parte)
    @editor = editor
    @parte = parte
    @anterior = nil
  end
  def ejecutar
    raise 'Ya ejecutado' unless @anterior.nil?
    @anterior = @editor.texto.dup
    @editor.agregar(@parte)
  end
  def deshacer
    raise 'Sin ejecución' if @anterior.nil?
    @editor.texto = @anterior
    @anterior = nil
  end
end

# CodeTrace | EJEMPLO DE CONSOLA (practica)
# 
# Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
#   ruby .\04-patrones-diseno\command\deshacer\main.rb
# Nota: comandos.rb es un componente; la salida corresponde al cliente main.rb.
# Salida esperada:
#   Hola
# CodeTrace | FIN EJEMPLO DE CONSOLA
