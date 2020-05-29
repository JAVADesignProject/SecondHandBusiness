package server.base;


import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public abstract class KServer {
    protected List<ClientHandler> clients;
    protected ServerSocket serverSocket;

    public KServer(int port)throws IOException{
        clients = new ArrayList<> ();
        serverSocket = new ServerSocket (port);
    }

    public abstract void update();

    public void remove(Object des){
        clients.remove (des);
    }

    public List<ClientHandler> getClients(){
        return clients;
    }
}
