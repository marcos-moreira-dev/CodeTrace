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
