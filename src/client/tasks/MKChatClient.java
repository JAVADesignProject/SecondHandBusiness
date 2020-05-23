package client.tasks;

import base.KClass;
import base.Message;
import base.json.MessageJson;
import client.listener.MKListener;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MKChatClient {
    private static MKChatClient instance;
    private MKSocket socket;
    private int token = -12222;
    private List<MKListener> msgListener = new ArrayList<>();

    public void addMsgListener(MKListener listener) {
        msgListener.add(listener);
    }

    public static MKChatClient getInstance() {
        return instance;
    }

    private Thread readThread = new Thread(()-> {
        try {
            while (true) {
                var msg = MessageJson.parse(Message.parse(socket.readUTF()).props);
                for (var i : msgListener) {
                    i.onReceiveMessage(msg);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    });

    public MKChatClient() throws IOException {
        instance = this;
        socket = new MKSocket(new Socket(KClass.HOST, KClass.CHAT_PORT));
        readThread.start();
    }

    public void register(int token) {
        this.token = token;
        try {
            socket.writeUTF(new Message(2, token, "").toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(MessageJson messageJson) {
        try {
            socket.writeUTF(new Message(0, token, messageJson.toString()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
