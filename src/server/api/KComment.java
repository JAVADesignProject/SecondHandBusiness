package server.api;

import base.Message;
import base.json.CommentJson;
import server.base.Database;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class KComment {
    public static Message addComment(CommentJson json){
        try {
            var sql = "INSERT INTO comment (reviewer_id, production_id, review_time, content) VALUES (?,?,?,?)";
            var ps = Database.getInstance ().getConn ().prepareStatement (sql);
            ps.setString (1,json.reviewer_id);
            ps.setInt (2,json.production_id);
            ps.setTimestamp (3, new Timestamp (json.time));
            ps.setString (4,json.text);
            ps.executeUpdate ();
            return new Message (0,0,"评论成功");
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        return null;
    }

    public static List<CommentJson> getComment(int productionID){
        try {
            var sql = "SELECT * FROM comment WHERE production_id=? ORDER BY review_time";
            var ps = Database.getInstance ().getConn ().prepareStatement (sql);
            ps.setInt (1,productionID);
            var rs = ps.executeQuery ();
            List<CommentJson> list = new ArrayList<> ();
            while (rs.next ()){
                CommentJson comment = new CommentJson ();
                comment.comment_id = rs.getInt ("id");
                comment.reviewer_id = rs.getString ("reviewer_id");
                comment.text = rs.getString ("content");
                comment.time = rs.getTimestamp ("review_time").getTime ();
                comment.production_id = productionID;
                list.add(comment);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        return null;
    }
}
