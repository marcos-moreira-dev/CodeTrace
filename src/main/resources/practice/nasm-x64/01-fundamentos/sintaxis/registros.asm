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
