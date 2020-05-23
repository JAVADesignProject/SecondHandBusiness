package sever.base;

import base.KSocket;

public abstract class ClientHandler {
    protected KSever sever;
    protected KSocket client;
    protected MessageHandler messageHandler = null;

    public ClientHandler(KSever sever, KSocket client) {
        this.sever = sever;
        this.client = client;
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void start(){
        update();
    }

    public abstract void update();
}
