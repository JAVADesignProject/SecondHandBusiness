package server.handler.chat;

import base.KSocket;
import base.Message;
import base.json.MessageJson;
import server.base.ClientHandler;
import server.base.KServer;
import server.server.KChatServer;

import java.io.IOException;

public class ChatClientHandler extends ClientHandler {
    private String userid = "-1000";

    public String getUserid(){
        return userid;
    }

    public ChatClientHandler(KServer sever, KSocket socket, ChatMessageHandler handler ) {
        super (sever, socket);
        this.messageHandler = handler;
        ((ChatMessageHandler) this.messageHandler).setClientHandler (this);
    }

    @Override
    public void update() {
        new Thread(()->{
            try {
                while (true){
                    var message = Message.parse(client.readUTF ());
                    messageHandler.handleMessage (message);
                }
            } catch (IOException e) {
                e.printStackTrace ( );
            }
        }).start ();
    }


    public void sendMessageToSever(MessageJson json){
        ((KChatServer)sever).sendMessage(json);
    }

    public void sendNewMessage(MessageJson message){
        try {
            client.writeUTF (new Message (0,0,message.toString ()).toString ());
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }

    public void setUserid(String props) {
        userid = props;
        ((KChatServer)sever).register(userid, this);
    }
}
