package sever.base;


import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public abstract class KSever {
    protected List<ClientHandler> clients;
    protected ServerSocket serverSocket;

    public KSever(int port)throws IOException{
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
