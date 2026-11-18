class Contador
  def initialize
    @valor = 0
  end

  def incrementar
    @valor += 1
  end
end

puts Contador.new.incrementar
