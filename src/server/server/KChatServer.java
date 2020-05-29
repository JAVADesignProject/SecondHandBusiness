package server.server;

import base.KSocket;
import base.json.MessageJson;
import server.api.KMessage;
//import sever.api.KUser;
import server.base.KServer;
import server.handler.chat.ChatClientHandler;
import server.handler.chat.ChatMessageHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KChatServer extends KServer {
    private Map<String, ChatClientHandler>chatMap = new HashMap<> ();
    private static KChatServer instance;

    public static KChatServer getInstance(){
        return instance;
    }

    public KChatServer(int port) throws IOException {
        super (port);
        instance = this;
    }

    @Override
    public void update() {
        while(true){
            try {
                var handler = new ChatClientHandler (this,new KSocket (serverSocket.accept ()), new ChatMessageHandler ());
                handler.start ();
                clients.add(handler);
            } catch (IOException e) {
                e.printStackTrace ( );
            }
        }
    }

    public void register(String userid, ChatClientHandler handler){
        logout (userid);
        chatMap.put (userid,handler);
    }

    private void sendMessageToClient(String userid, MessageJson message){
        if(chatMap.containsKey (userid)){
            var receiver = chatMap.get (userid);
            if(receiver == null)
                return;
            receiver.sendNewMessage (message);
        }
    }

    public void sendMessage(MessageJson message){
        sendMessageToClient (message.sender,message);
        sendMessageToClient (message.receiver,message);
        KMessage.addMessage (message);
        System.out.println("插入信息");
    }

    public void logout(String userid){
        chatMap.remove (userid);
    }
}
