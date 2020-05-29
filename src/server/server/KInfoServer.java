package server.server;

import base.KSocket;
import server.base.KServer;
import server.handler.info.InfoClientHandler;
import server.handler.info.InfoMessageHandler;

import java.io.IOException;

public class KInfoServer extends KServer {


    public KInfoServer(int port) throws IOException {
        super (port);
    }

    @Override
    public void update() {
        while(true){
            try {
                var handler = new InfoClientHandler (this,new KSocket (serverSocket.accept ()),new InfoMessageHandler ());
                handler.start ();
                clients.add(handler);
            } catch (IOException e) {
                e.printStackTrace ( );
            }
        }
    }
}
