; CodeTrace | Referencia: NASM x86-64 / Linux ELF64 (WSL)
; Comprobar: nasm -f elf64 ".\03-algoritmos/busqueda-binaria/busqueda_binaria.asm" -o app.o; ld app.o -o app
; Ejecutar: ./app

; Cliente de búsqueda binaria sobre una región contigua de memoria.
;
; RDI representa el límite inferior y RSI el superior (semiabierto).
; Cada salto descarta la mitad de los candidatos, por lo que hay
; O(log n) comparaciones y O(1) registros auxiliares.
; Código de salida Linux: 0 si encuentra 12, 1 si no encuentra.
global _start
section .data
    datos dq 2, 5, 8, 12, 21, 34, 55
    cantidad equ 7
section .text
_start:
    xor rdi, rdi           ; lo = 0
    mov rsi, cantidad      ; hi = 7
ciclo:
    cmp rdi, rsi
    jae no_encontrado
    mov r8, rsi
    sub r8, rdi
    shr r8, 1
    add r8, rdi           ; mid = lo + (hi-lo)/2
    mov rax, [datos + r8*8]
    cmp rax, 12
    je encontrado
    jb mayor
    mov rsi, r8           ; el objetivo es menor
    jmp ciclo
mayor:
    lea rdi, [r8 + 1]     ; el objetivo es mayor
    jmp ciclo
encontrado:
    xor edi, edi
    jmp salir
no_encontrado:
    mov edi, 1
salir:
    mov eax, 60           ; sys_exit
    syscall

; CodeTrace | EJEMPLO DE CONSOLA (practica)
; 
; Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
;   wsl nasm -f elf64 03-algoritmos/busqueda-binaria/busqueda_binaria.asm -o /tmp/ct-ejemplo.o
;   wsl ld /tmp/ct-ejemplo.o -o /tmp/ct-ejemplo
;   wsl sh -c '/tmp/ct-ejemplo; printf "codigo-de-salida:%s\n" "$?"'
; Salida esperada:
;   codigo-de-salida:0
; CodeTrace | FIN EJEMPLO DE CONSOLA
