package base;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class KSocket {
    private final Socket socket;
    private final DataInputStream reader;
    private final DataOutputStream writer;
    public static final int WRITE_READ_UTF_MAX_LENGTH = 65000;//65535

    public KSocket(Socket socket) throws IOException {
        this.socket = socket;
        reader = new DataInputStream (socket.getInputStream ());
        writer = new DataOutputStream (socket.getOutputStream ());
    }

    public KSocket(String host,int port)throws IOException{
        this(new Socket (host, port));
    }

    public String readUTF() throws IOException{
        String temp = reader.readUTF ();
        StringBuilder builder = new StringBuilder();
        while (temp.length() >= WRITE_READ_UTF_MAX_LENGTH) {
            builder.append(temp);
            temp = reader.readUTF ();
        }
        builder.append(temp);
        return builder.toString();
    }

    public void writeUTF(String line)throws IOException{
        if (line.length() > WRITE_READ_UTF_MAX_LENGTH) {
            for (int i = 1; i < line.length() / WRITE_READ_UTF_MAX_LENGTH + 2; i++) {
                writer.writeUTF(line.substring(WRITE_READ_UTF_MAX_LENGTH * (i - 1), Math.min(WRITE_READ_UTF_MAX_LENGTH * i, line.length())));
            }
        } else {
            writer.writeUTF(line);
        }
        writer.flush();
    }

    public void write(byte[] bytes)throws IOException{
        writer.write (bytes);
        writer.flush ();
    }

    public int read(byte[] bytes,int length)throws IOException{
        return reader.read (bytes,0,length);
    }

    public void closeReader()throws IOException{
        socket.shutdownInput ();
    }

    public void claseWriter()throws IOException{
        socket.shutdownOutput ();
    }

    public void closeALL()throws IOException{
        reader.close ();
        writer.close ();
        socket.close ();
    }

    public void close()throws IOException{
        socket.close ();
    }
}
