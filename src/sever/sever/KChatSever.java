package sever.sever;

import base.KSocket;
import base.json.MessageJson;
import sever.api.KMessage;
import sever.api.KUser;
import sever.base.ClientHandler;
import sever.base.KSever;
import sever.handler.chat.ChatClientHandler;
import sever.handler.chat.ChatMessageHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KChatSever extends KSever {
    private Map<String, ChatClientHandler>chatMap = new HashMap<> ();
    private static KChatSever instance;

    public static KChatSever getInstance(){
        return instance;
    }

    public KChatSever(int port) throws IOException {
        super (port);
        instance = this;
    }

    @Override
    public void update() {
        while(true){
            try {
                var handler = new ChatClientHandler (this,new KSocket (serverSocket.accept ()), new ChatMessageHandler ());
                handler.start ();
                ;
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
            if(receiver == null) return;
            receiver.sendNewMessage (message);
            KMessage.addMessage (message);
        }
    }

    public void sendMessage(MessageJson message){
        sendMessageToClient (message.sender,message);
        sendMessageToClient (message.receiver,message);
    }

    public void logout(String userid){
        chatMap.remove (userid);
    }
}
