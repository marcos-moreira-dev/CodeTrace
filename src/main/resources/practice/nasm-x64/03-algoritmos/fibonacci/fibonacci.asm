; CodeTrace | Referencia: NASM x86-64, Fibonacci iterativo (Linux System V)
; Compilar: nasm -f elf64 fibonacci.asm -o fibonacci.o
; rdi = n; rax = F(n), resultado modulo 2^64
bits 64
section .text
global fibonacci

fibonacci:
    xor rax, rax
    mov r8, 1
    xor rcx, rcx
.bucle:
    cmp rcx, rdi
    jge .fin
    mov rdx, rax
    add rdx, r8
    mov rax, r8
    mov r8, rdx
    inc rcx
    jmp .bucle
.fin:
    ret

; CodeTrace | EJEMPLO DE CONSOLA (practica)
; 
; Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
;   wsl nasm -f elf64 03-algoritmos/fibonacci/fibonacci.asm -o /tmp/ct-ejemplo.o
; Nota: archivo reutilizable sin entrada; el comando solo verifica la compilación o sintaxis.
; Salida esperada:
;   (sin salida de consola; la comprobación termina correctamente)
; CodeTrace | FIN EJEMPLO DE CONSOLA
