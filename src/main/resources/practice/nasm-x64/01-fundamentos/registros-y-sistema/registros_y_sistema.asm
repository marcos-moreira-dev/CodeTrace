; CodeTrace | Referencia: NASM x86-64 / Linux ELF64 (WSL)
; Comprobar: nasm -f elf64 ARCHIVO.asm -o ARCHIVO.o; ld ARCHIVO.o -o app
; Ejecutar: ./app

; Cliente de registros y llamada al sistema en Linux x86-64.
;
; El ABI de syscall utiliza RAX como número de servicio y RDI como
; primer argumento. Linux syscall 60 termina el proceso. Esto NO
; es un ejecutable nativo Windows; debe montarse en Linux o WSL.
global _start
section .text
_start:
    mov rax, 60             ; sys_exit
    xor rdi, rdi            ; código de salida cero
    syscall

; CodeTrace | EJEMPLO DE CONSOLA (practica)
; 
; Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
;   wsl nasm -f elf64 01-fundamentos/registros-y-sistema/registros_y_sistema.asm -o /tmp/ct-ejemplo.o
;   wsl ld /tmp/ct-ejemplo.o -o /tmp/ct-ejemplo
;   wsl sh -c '/tmp/ct-ejemplo; printf "codigo-de-salida:%s\n" "$?"'
; Salida esperada:
;   codigo-de-salida:0
; CodeTrace | FIN EJEMPLO DE CONSOLA
