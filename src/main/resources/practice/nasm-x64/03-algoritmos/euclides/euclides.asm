; CodeTrace | Referencia: NASM x86-64, algoritmo de Euclides, System V (Linux)
; Compilar: nasm -f elf64 euclides.asm -o euclides.o
; rdi=a, rsi=b (enteros sin signo); rax = mcd
bits 64
section .text
global mcd

mcd:
    mov rax, rdi
    mov rcx, rsi
.bucle:
    test rcx, rcx
    jz .fin
    xor rdx, rdx
    div rcx
    mov rax, rcx
    mov rcx, rdx
    jmp .bucle
.fin:
    ret

; CodeTrace | EJEMPLO DE CONSOLA (practica)
; 
; Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
;   wsl nasm -f elf64 03-algoritmos/euclides/euclides.asm -o /tmp/ct-ejemplo.o
; Nota: archivo reutilizable sin entrada; el comando solo verifica la compilación o sintaxis.
; Salida esperada:
;   (sin salida de consola; la comprobación termina correctamente)
; CodeTrace | FIN EJEMPLO DE CONSOLA
