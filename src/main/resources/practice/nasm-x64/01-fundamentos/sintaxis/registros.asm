; CodeTrace | Referencia: NASM x86-64, sintaxis Intel, ABI System V (Linux)
; Compilar: nasm -f elf64 registros.asm -o registros.o
; Entrada: rdi y rsi; salida: rax
bits 64
default rel
section .text
global sumar_dos

sumar_dos:
    mov rax, rdi
    add rax, rsi
    ret

; CodeTrace | EJEMPLO DE CONSOLA (practica)
; 
; Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
;   wsl nasm -f elf64 01-fundamentos/sintaxis/registros.asm -o /tmp/ct-ejemplo.o
; Nota: archivo reutilizable sin entrada; el comando solo verifica la compilación o sintaxis.
; Salida esperada:
;   (sin salida de consola; la comprobación termina correctamente)
; CodeTrace | FIN EJEMPLO DE CONSOLA
