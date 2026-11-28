// Tema:
// Un Selector multiplica canales no bloqueantes sobre un hilo. Una clave cambia de OP_READ a OP_WRITE cuando se han acumulado cuatro bytes; el buffer conserva la posición en operaciones parciales. Se limita a una conexión y a un plazo fijo.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 ServidorSelector.java Cliente.java MainSelector.java
// Ejecutar: java MainSelector

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class ServidorSelector implements AutoCloseable {
    private final Selector selector = Selector.open();
    private final ServerSocketChannel escucha = ServerSocketChannel.open();

    public ServidorSelector() throws IOException {
        escucha.configureBlocking(false);
        escucha.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), 0));
        escucha.register(selector, SelectionKey.OP_ACCEPT);
    }
    public int puerto() throws IOException {
        return ((InetSocketAddress) escucha.getLocalAddress()).getPort();
    }
    public void atender() throws IOException {
        long fin = System.nanoTime() + TimeUnit.SECONDS.toNanos(5);
        try {
            while (System.nanoTime() < fin) {
                selector.select(100);
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey clave = it.next();
                    it.remove();
                    if (!clave.isValid()) continue;
                    if (clave.isAcceptable()) {
                        SocketChannel cliente = escucha.accept();
                        if (cliente != null) {
                            cliente.configureBlocking(false);
                            cliente.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(4));
                            clave.cancel(); // Solo admitimos una conexión.
                            escucha.close();
                        }
                    } else if (clave.isReadable()) {
                        SocketChannel cliente = (SocketChannel) clave.channel();
                        ByteBuffer b = (ByteBuffer) clave.attachment();
                        int n = cliente.read(b);
                        if (n == -1) throw new EOFException("Mensaje truncado");
                        if (!b.hasRemaining()) {
                            b.flip(); // El mismo buffer será la respuesta Echo.
                            clave.interestOps(SelectionKey.OP_WRITE);
                        }
                    } else if (clave.isWritable()) {
                        SocketChannel cliente = (SocketChannel) clave.channel();
                        ByteBuffer b = (ByteBuffer) clave.attachment();
                        cliente.write(b);
                        if (!b.hasRemaining()) {
                            clave.cancel();
                            cliente.close();
                            return;
                        }
                    }
                }
            }
            throw new IOException("Plazo agotado");
        } finally {
            for (SelectionKey clave : selector.keys()) clave.channel().close();
        }
    }
    public void close() throws IOException {
        try { escucha.close(); } finally { selector.close(); }
    }
}

// Salida esperada:
// HOLA
