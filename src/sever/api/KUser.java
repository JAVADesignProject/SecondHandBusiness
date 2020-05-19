package sever.api;

import base.Message;
import base.json.UserJson;
import sever.base.Database;
import sever.base.KSeverManager;

import java.sql.SQLException;

public class KUser {
    public static Message login(UserJson user)  {
        try {
            if(checkAccount (user)){
                setUserStatus (user.userid,1);
                var token = 0;
                while(KSeverManager.containsToken (token)){
                    token =(int)(Math.random () * 1e5);
                }
                KSeverManager.addToken (token,user.userid);
                return new Message (0,token,"登录成功");
            }else{
                return new Message (-1,Integer.MAX_VALUE,"登录失败");
            }
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        return null;
    }

    private static boolean checkAccount(UserJson user)  throws SQLException{
        var sql = "SELECT id FROM user WHERE id=? AND password=?;";
        var ps = Database.getInstance ().getConn ().prepareStatement (sql);
        ps.setString (1, String.valueOf (user.userid));
        ps.setString (2,user.password);
        var rs = ps.executeQuery ();
        return rs.next ();
    }

    private static void setUserStatus(String userid,int status){
        try {
            var updateStatus = "UPDATE user SET status=? WHERE id=?;";
            var ps = Database.getInstance ().getConn ().prepareStatement (updateStatus);
            ps.setInt (1,status);
            ps.setString (2,userid);
            ps.executeQuery ();
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
    }

    public static void logout(int token) {
        var userid = KSeverManager.getUserId(token);
        setUserStatus (userid,0);
    }


    public static int getStatus(int token){
        var userid = KSeverManager.getUserId(token);
        try {
            var updateStatus ="SELECT status FROM user WHERE id=?;";
            var ps = Database.getInstance ().getConn ().prepareStatement (updateStatus);
            ps.setString (1, String.valueOf (userid));
            var rs = ps.executeQuery ();
            if(rs.next()){
                return rs.getInt (1);
            }
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        return 0;
    }

    public static UserJson getUserInfo(String userid){
        try {
            var updateStatus = "SELECT * FROM user WHERE id=?;";
            var ps = Database.getInstance ().getConn ().prepareStatement (updateStatus);
            ps.setString (1,userid);
            var rs = ps.executeQuery ();
            if(rs.next ()){
                var user = new UserJson();
                user.userid = userid;
                user.password = "你觉得这个会和你说嘛";
                user.status = rs.getInt ("status");
                user.username = rs.getString ("username");
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        return null;
    }

    public static Message register(UserJson user){
        try {
            if(getUserInfo (user.userid)!=null){
                return new Message (-1,0,"学号已存在");
            } else if (user.userid.length ( ) != 12) {
                return new Message(-2,0,"学号错误");
            }
            var sql = "INSERT INTO user(userid,password,username,status)VALUES(?,?,?,0);";
            var ps = Database.getInstance ().getConn ().prepareStatement (sql);
            ps.setString (1,user.userid);
            ps.setString (2,user.password);
            ps.setString (3,user.username);
            ps.executeUpdate ();
            return new Message (0,0,"注册成功");
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        return null;
    }
}
