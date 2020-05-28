package client.tasks;

import base.KClass;
import base.Message;
import base.Parser;
import base.json.CommentJson;
import base.json.MessageJson;
import base.json.ProductionJson;
import base.json.UserJson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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

    public synchronized List<UserJson> getChatUser(UserJson user) {
        var result = post(new Message(KClass.MY_CHAT, token, user.toString()));
        if (result.code < 0)
            return new ArrayList<>();
        return Parser.fromJson(result.props, new TypeToken<List<UserJson>>() {}.getType());
    }

    public synchronized List<MessageJson> getMessage(MessageJson info) {
        var result = post(new Message(KClass.CHAT_MSG, token, info.toString()));
        if (result.code < 0)
            return new ArrayList<>();
        return Parser.fromJson(result.props, new TypeToken<List<MessageJson>>() {}.getType());
    }

    public synchronized void addProduction(ProductionJson production) {
        post(new Message(KClass.ADD_PRODUCTION, token, production.toString()));
    }

    public synchronized List<ProductionJson> getProduction() {
        var result = post(new Message(KClass.PRODUCTION_INFO, token, null));
        return Parser.fromJson(result.props, new TypeToken<List<ProductionJson>>(){}.getType());
    }

    public synchronized void addComment(CommentJson comment) {
        post(new Message(KClass.ADD_COMMENT, token, comment.toString()));
    }

    public synchronized List<CommentJson> getComment(int productionID) {
        var result = post(new Message(KClass.GET_COMMENT, token, Integer.toString(productionID)));
        return Parser.fromJson(result.props, new TypeToken<List<CommentJson>>(){}.getType());
    }

    public synchronized void buyNormalProduction(ProductionJson production) {
        post(new Message(KClass.NORMAL_PRODUCTION_BUY, token, production.toString()));
    }

    public synchronized Message addComment(CommentJson comment){
        return post(new Message (KClass.ADD_COMMENT, token, comment.toString ()));
    }

    public synchronized Message getComment(ProductionJson production){
        return post(new Message (KClass.GET_COMMENT, token, production.toString ()));
    }

    public synchronized Message search(String keyword){
        return post(new Message (KClass.SEARCH, token, keyword));
    }

    public synchronized Message buyNormalProduction(ProductionJson production){
        return post(new Message (KClass.NORMAL_PRODUCTION_BUY, token, production.toString ()));
    }

    public synchronized  Message buyAuctionProduction(ProductionJson production){
        return post (new Message (KClass.AUCTION_PRODUCTION_BUY, token, production.toString ()));
    }

    public synchronized Message getMyProduction(UserJson user){
        return post (new Message (KClass.MY_PRODUCTION, token, user.toString ()));
    }

    public synchronized Message deleteProduction(ProductionJson production){
        return post(new Message (KClass.DELETE_PRODUCTION, token, production.toString ()));
    }
}
