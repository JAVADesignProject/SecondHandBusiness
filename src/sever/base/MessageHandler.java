package sever.base;

import base.Message;

public abstract class MessageHandler {
    public abstract Message handleMessage(Message message);

}
