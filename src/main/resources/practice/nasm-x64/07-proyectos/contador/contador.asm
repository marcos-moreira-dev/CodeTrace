; CodeTrace | Referencia: NASM x86-64, contador hasta n (System V Linux)
; Compilar: nasm -f elf64 contador.asm -o contador.o
; rdi = n; devuelve 0 si n=0, o la suma de impares < n
bits 64
section .text
global sumar_impares

sumar_impares:
    xor rax, rax
    mov rcx, 1
.bucle:
    cmp rcx, rdi
    jge .fin
    add rax, rcx
    add rcx, 2
    jmp .bucle
.fin:
    ret
