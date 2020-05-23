package sever.sever;

import base.KSocket;
import sever.base.KSever;
import sever.handler.info.InfoClientHandler;
import sever.handler.info.InfoMessageHandler;

import java.io.IOException;

public class KInfoSever extends KSever {


    public KInfoSever(int port) throws IOException {
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
