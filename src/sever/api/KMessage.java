package sever.api;

import base.Message;
import base.Parser;
import base.json.MessageJson;
import base.json.UserJson;
import sever.base.Database;
import sever.base.KSeverManager;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class KMessage {
    public static Message getNewChat(int token){
        try {
            var userid = KSeverManager.getUserId (token);
            var sql = "SELECT sender FROM message WHERE receiver=? GROUP BY sender";
            var ps = Database.getInstance ().getConn ().prepareStatement (sql);
            ps.setString (1,userid);
            var rs = ps.executeQuery ();
            List<UserJson> list = new ArrayList<> ();
            while(rs.next ()){
                var u = KUser.getUserInfo (rs.getString ("sender"));
                if(u != null)
                    list.add(u);
            }
            return new Message (0,0, Parser.toJson (list));
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
            var sql = "SELECT * FROM message WHERE receiver=? AND sender=? ORDER BY time;";
            var ps = Database.getInstance ().getConn ().prepareStatement (sql);
            ps.setString (1,messageJson.receiver);
            ps.setString (2,messageJson.sender);
            var rs = ps.executeQuery ();
            List<MessageJson> list = new ArrayList<> ();
            while(rs.next ()){
                var msg = new MessageJson ();
                msg.msgid = rs.getInt ("id");
                msg.receiver = rs.getString ("receiver");
                msg.sender = rs.getString ("sender");
                msg.text = rs.getString ("test");
                msg.time = rs.getTimestamp ("time").getTime ();
                list.add(msg);
                //阅读过后就移除这条消息
                removeMessage (msg.msgid);
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
            var sql = "INSERT INTO message (sender, receiver,text,time) VALUES(?,?,?,?);";
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
