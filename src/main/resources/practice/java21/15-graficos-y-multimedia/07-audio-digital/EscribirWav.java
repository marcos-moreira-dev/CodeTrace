// Tema:
// Java Sound escribe WAV PCM mono de 16 bits little-endian, sin abrir dispositivo de reproducción. Se crea una señal sintética de 80 muestras a 8000 Hz y se comprueba el formato al leerla.
// Modalidad: ejecución real con datos locales.
// Compilar: javac --release 21 -encoding UTF-8 EscribirWav.java
// Ejecutar: java EscribirWav

import java.io.ByteArrayInputStream;
import java.nio.file.*;
import javax.sound.sampled.*;

public class EscribirWav {
    public static void main(String[] args) throws Exception {
        int tasa = 8000, muestras = 80;
        byte[] pcm = new byte[muestras * 2];
        for (int n = 0; n < muestras; n++) {
            short valor = (short) Math.round(10000 * Math.sin(2 * Math.PI * 400 * n / tasa));
            pcm[2 * n] = (byte) valor;
            pcm[2 * n + 1] = (byte) (valor >>> 8);
        }
        AudioFormat formato = new AudioFormat(tasa, 16, 1, true, false);
        Path archivo = Files.createTempFile("audio-", ".wav");
        try {
            try (AudioInputStream entrada = new AudioInputStream(new ByteArrayInputStream(pcm), formato, muestras)) {
                AudioSystem.write(entrada, AudioFileFormat.Type.WAVE, archivo.toFile());
            }
            try (AudioInputStream leido = AudioSystem.getAudioInputStream(archivo.toFile())) {
                System.out.println("Muestras: " + leido.getFrameLength());
                System.out.println("Tasa: " + (int) leido.getFormat().getSampleRate());
                System.out.println("Canales: " + leido.getFormat().getChannels());
            }
        } finally { Files.deleteIfExists(archivo); }
    }
}

// Salida esperada:
// Muestras: 80
// Tasa: 8000
// Canales: 1
