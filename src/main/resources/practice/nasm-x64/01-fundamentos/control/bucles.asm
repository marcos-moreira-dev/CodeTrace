; CodeTrace | Referencia: NASM x86-64, sintaxis Intel, ABI System V (Linux)
; Compilar: nasm -f elf64 bucles.asm -o bucles.o
; Suma n + (n-1) + ... + 1 para n >= 0, rdi -> rax
bits 64
section .text
global sumar_hasta_n

sumar_hasta_n:
    xor rax, rax
    test rdi, rdi
    jz .fin
.bucle:
    add rax, rdi
    dec rdi
    jnz .bucle
.fin:
    ret

; CodeTrace | EJEMPLO DE CONSOLA (practica)
; 
; Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
;   wsl nasm -f elf64 01-fundamentos/control/bucles.asm -o /tmp/ct-ejemplo.o
; Nota: archivo reutilizable sin entrada; el comando solo verifica la compilación o sintaxis.
; Salida esperada:
;   (sin salida de consola; la comprobación termina correctamente)
; CodeTrace | FIN EJEMPLO DE CONSOLA
