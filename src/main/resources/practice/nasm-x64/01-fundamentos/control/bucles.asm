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
