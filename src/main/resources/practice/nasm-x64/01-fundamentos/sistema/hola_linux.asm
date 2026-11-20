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

; CodeTrace | EJEMPLO DE CONSOLA (practica)
; 
; Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
;   wsl nasm -f elf64 01-fundamentos/sistema/hola_linux.asm -o /tmp/ct-ejemplo.o
;   wsl ld /tmp/ct-ejemplo.o -o /tmp/ct-ejemplo
;   wsl sh -c '/tmp/ct-ejemplo; printf "codigo-de-salida:%s\n" "$?"'
; Salida esperada:
;   Hola desde CodeTrace
;   codigo-de-salida:0
; CodeTrace | FIN EJEMPLO DE CONSOLA
