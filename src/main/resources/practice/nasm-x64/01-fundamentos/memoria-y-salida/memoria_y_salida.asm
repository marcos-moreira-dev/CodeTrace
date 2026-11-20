; CodeTrace | Referencia: NASM x86-64 / Linux ELF64 (WSL)
; Comprobar: nasm -f elf64 ".\01-fundamentos/memoria-y-salida/memoria_y_salida.asm" -o app.o; ld app.o -o app
; Ejecutar: ./app

; Cliente de secciones .data, registros y syscall write.
;
; La etiqueta mensaje representa dirección de memoria; longitud
; representa bytes contiguos, NO caracteres Unicode. Linux x86-64
; recibe syscall 1 con fd en RDI, buffer en RSI y bytes en RDX.
; El ejecutable ELF64 se ensambla y corre en Linux/WSL.
global _start
section .data
    mensaje db 'Hola desde NASM', 10
    longitud equ $ - mensaje
section .text
_start:
    mov eax, 1
    mov edi, 1
    lea rsi, [rel mensaje]
    mov edx, longitud
    syscall
    mov eax, 60
    xor edi, edi
    syscall

; CodeTrace | EJEMPLO DE CONSOLA (practica)
; 
; Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
;   wsl nasm -f elf64 01-fundamentos/memoria-y-salida/memoria_y_salida.asm -o /tmp/ct-ejemplo.o
;   wsl ld /tmp/ct-ejemplo.o -o /tmp/ct-ejemplo
;   wsl sh -c '/tmp/ct-ejemplo; printf "codigo-de-salida:%s\n" "$?"'
; Salida esperada:
;   Hola desde NASM
;   codigo-de-salida:0
; CodeTrace | FIN EJEMPLO DE CONSOLA
