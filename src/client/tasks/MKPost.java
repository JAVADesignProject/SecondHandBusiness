package client.tasks;

import java.io.IOException;
import java.net.Socket;

/**
 * 建立与服务器的连接并获取信息，返回结果大于等于0为成功，否则为失败
 */
public class MKPost {
    private int token = Integer.MAX_VALUE;
    private static MKPost instance;
    private MKSocket socket;

    public static MKPost getInstance() {
        return instance;
    }

    public MKPost() throws IOException {
        instance = this;
        socket = new MKSocket(new Socket(/*GClass.HOST, GClass.INFO_PORT*/));
    }

//    private synchronized Message post(Message message) {
//        try {
//            socket.writeUTF(message.toString());
//            return Message.parse(socket.readUTF());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new Message(-128, 0, "空结果错误");
//    }


}
