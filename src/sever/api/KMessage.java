package sever.api;

import base.Message;
import base.Parser;
import base.json.MessageJson;
import base.json.UserJson;
import sever.base.Database;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class KMessage {
    /**
     * @description: 查询应该被加入到消息列表中的用户
     * @param user 当前登录用户的用户ID
     * @return Message 返回带有查询到的用户信息的一个message对象
     */
    public static Message getNewChat(UserJson user){
        try {
            var userID = user.userID;
            var sql = "SELECT receiver_id, sender_id FROM message WHERE sender_id = ? OR receiver_id = ? GROUP BY sender_id, receiver_id";
            var ps = Database.getInstance ().getConn ().prepareStatement (sql);
            ps.setString (1, userID);
            ps.setString (2, userID);
            var rs = ps.executeQuery ();
            List<UserJson> list = new ArrayList<> ();
            while(rs.next ()){
                if (rs.getString("sender_id").equals(userID)) {
                    if (!list.contains(KUser.getUserInfo (rs.getString ("receiver_id")))) {
                        var u = KUser.getUserInfo(rs.getString("receiver_id"));
                        if(u != null)
                            list.add(u);
                    }
                } else {
                    if (!list.contains(KUser.getUserInfo (rs.getString ("sender_id")))) {
                        var u = KUser.getUserInfo (rs.getString ("sender_id"));
                        if(u != null)
                            list.add(u);
                    }
                }
            }
            return new Message (0,0, Parser.toJson(list));
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        return null;
    }

    public static boolean removeMessage(int msgid){
        try {
            var sql = "DELETE FROM message WHERE id=?;";
            var ps = Database.getInstance ().getConn ().prepareStatement (sql);
            ps.setInt (1,msgid);
            ps.executeUpdate ();
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        return false;
    }

    private static List<MessageJson> getNormalMessage(MessageJson messageJson){
        try {
            var sql = "SELECT * FROM message WHERE receiver_id=? AND sender_id=? OR receiver_id=? AND sender_id=? ORDER BY time;";
            var ps = Database.getInstance ().getConn ().prepareStatement (sql);
            ps.setString (1, messageJson.receiver);
            ps.setString (2, messageJson.sender);
            ps.setString (3, messageJson.sender);
            ps.setString (4, messageJson.receiver);
            var rs = ps.executeQuery ();
            List<MessageJson> list = new ArrayList<> ();
            while(rs.next ()){
                var msg = new MessageJson ();
                msg.msgid = rs.getInt ("id");
                msg.receiver = rs.getString ("receiver_id");
                msg.sender = rs.getString ("sender_id");
                msg.text = rs.getString ("text");
                msg.time = rs.getTimestamp ("time").getTime ();
                list.add(msg);
                //阅读过后就移除这条消息
                //removeMessage (msg.msgid);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        return new ArrayList<> ();
    }

    public static Message getMessage(int token, MessageJson messageJson){
        return new Message (0,0,Parser.toJson (getNormalMessage (messageJson)));
    }

    public static void addMessage(MessageJson json){
        try {
            var sql = "INSERT INTO message (sender_id, receiver_id, text, time) VALUES(?,?,?,?);";
            var ps = Database.getInstance ().getConn ().prepareStatement (sql);
            ps.setString (1,json.sender);
            ps.setString (2,json.receiver);
            ps.setString (3,json.text);
            ps.setTimestamp (4,new Timestamp (json.time));
            ps.executeUpdate ();
        } catch (SQLException e) {
            e.printStackTrace ( );
        }

    }
}
