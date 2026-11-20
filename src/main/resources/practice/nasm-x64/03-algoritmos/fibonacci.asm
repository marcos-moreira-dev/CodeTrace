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
