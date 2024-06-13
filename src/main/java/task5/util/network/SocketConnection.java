package task5.util.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketConnection {
    private final Socket socket;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;

    public SocketConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.inputStream = new ObjectInputStream(socket.getInputStream());
        this.outputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    public void write(Packet packet) throws IOException {
        this.outputStream.writeObject(packet);
    }

    public Packet read() throws IOException, ClassNotFoundException {
        return (Packet) this.inputStream.readObject();
    }
}
