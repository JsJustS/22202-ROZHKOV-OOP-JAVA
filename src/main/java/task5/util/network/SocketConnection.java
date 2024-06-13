package task5.util.network;

import java.io.*;
import java.net.Socket;

public class SocketConnection {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public SocketConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public void write(PacketBuf packet) throws IOException {
        this.outputStream.writeObject(packet.asArray());
    }

    public PacketBuf read() throws IOException, ClassNotFoundException {
        return new PacketBuf((byte[]) this.inputStream.readObject());
    }

    public void stop() throws IOException {
        this.socket.close();
    }
}
