package server.handler.chat;

import base.KClass;
import base.Message;
import base.json.MessageJson;
import server.base.KServerManager;
import server.base.MessageHandler;

public class ChatMessageHandler extends MessageHandler {
    private ChatClientHandler clientHandler;

    public void setClientHandler(ChatClientHandler clientHandler){
        this.clientHandler = clientHandler;
    }

    @Override
    public Message handleMessage(Message message) {
        switch(message.code){
            case KClass.LOGIN:
                var json = MessageJson.parse (message.props);
                clientHandler.sendMessageToSever(json);
                break;
            case KClass.USER_INFO:
                clientHandler.setUserid (KServerManager.getUserId (message.token));
        }
        return null;
    }
}
