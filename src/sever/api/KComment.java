package sever.api;

import base.Message;
import base.json.CommentJson;
import sever.base.Database;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class KComment {
    public static Message addComment(CommentJson json){
        try {
            var sql = "INSERT INTO comment (reviewed_id,production_id,review_time,content)";
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

    public static List<CommentJson> getComment(int proid){
        try {
            var sql = "SELECT * FROM comment WHERE porduction_id=? ORDER BY ";
            var ps = Database.getInstance ().getConn ().prepareStatement (sql);
            ps.setInt (1,proid);
            var rs = ps.executeQuery ();
            List<CommentJson> list = new ArrayList<> ();
            while (rs.next ()){
                CommentJson comment = new CommentJson ();
                comment.comment_id = rs.getInt ("comment_id");
                comment.reviewer_id = rs.getString ("revew_id");
                comment.text = rs.getString ("content");
                comment.time = rs.getTimestamp ("review_time").getTime ();
                comment.production_id = proid;
                list.add(comment);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        return null;
    }
}
