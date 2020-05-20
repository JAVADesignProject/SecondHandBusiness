package client.tasks;

import base.KClass;
import base.Message;
import base.json.UserJson;

import java.io.IOException;
import java.net.Socket;

/**
 * 建立与服务器的连接并获取信息，返回结果大于等于0为成功，否则为失败
 */
public class MKPost {
    private int token = Integer.MAX_VALUE;
    private static MKPost instance;
    private final MKSocket socket;

    public static MKPost getInstance() {
        return instance;
    }

    public MKPost() throws IOException {
        instance = this;
        socket = new MKSocket(new Socket(KClass.HOST, KClass.INFO_PORT));
    }

    private synchronized Message post(Message message) {
        try {
            socket.writeUTF(message.toString());
            return Message.parse(socket.readUTF());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Message(-128, 0, "空结果错误");
    }

    public synchronized Message login(UserJson user) {
        var result = post(new Message(KClass.LOGIN, token, user.toString()));
        if (result.code >= 0) token = result.token;
        return result;
    }

    public synchronized Message register(UserJson user) {
        return post(new Message(KClass.REGISTER, token, user.toString()));
    }

    public synchronized Message updatePassword(UserJson user) {
        return post(new Message(KClass.UPDATE_PASSWORD, token, user.toString()));
    }

    public synchronized UserJson getUserInfo(String userid) {
        return getUserInfo(new UserJson(userid));
    }

    public synchronized UserJson getUserInfo(UserJson user) {
        var result = post(new Message(KClass.USER_INFO, token, user.toString()));
        return result.code < 0 ? null : UserJson.parse(result.props);
    }


}
