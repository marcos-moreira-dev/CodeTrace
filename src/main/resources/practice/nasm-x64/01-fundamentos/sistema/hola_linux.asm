; CodeTrace | Referencia: NASM x86-64, Linux syscalls (NO ejecutable Windows)
; Compilar: nasm -f elf64 hola_linux.asm -o hola_linux.o
; Enlazar: ld hola_linux.o -o hola_linux
; Ejecutar: ./hola_linux
bits 64
default rel
section .data
    mensaje db "Hola desde CodeTrace", 10
    longitud equ $ - mensaje
section .text
global _start

_start:
    mov rax, 1
    mov rdi, 1
    lea rsi, [rel mensaje]
    mov rdx, longitud
    syscall

    mov rax, 60
    xor rdi, rdi
    syscall
