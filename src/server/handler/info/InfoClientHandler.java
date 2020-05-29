package server.handler.info;

import base.KSocket;
import base.Message;
import server.base.ClientHandler;
import server.base.KServer;
import server.base.MessageHandler;

import java.io.IOException;

public class InfoClientHandler extends ClientHandler {

    public InfoClientHandler(KServer sever, KSocket client, MessageHandler infoHandler) {
        super (sever, client);
        this.messageHandler = infoHandler;
    }

    @Override
    public void update() {
        new Thread (()->{
            try {
                while(true){
                    //获取消息
                    var request = Message.parse(client.readUTF ());
                    //获取执行结果
                    var result = messageHandler.handleMessage (request);
                    //写入结果
                    if(result!=null) client.writeUTF (result.toString ());
                    else client.writeUTF ("");
                }
            } catch (IOException e) {
                //e.printStackTrace ( );
                ((InfoMessageHandler)messageHandler).logout ();
                sever.remove (this);
            }
        }).start ();
    }
}
