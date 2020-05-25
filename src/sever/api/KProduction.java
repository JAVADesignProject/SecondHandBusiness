package sever.api;

import base.Message;
import base.json.ProductionJson;
import sever.base.Database;

import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class KProduction {
    //添加商品
    public static Message addProduction(ProductionJson json){
        try {
            var sql = "INSERT INTO production (name, price, producer_id, post_time, introduction, auction, image) VALUES (?,?,?,?,?,?,?)";
            var ps = Database.getInstance ().getConn ().prepareStatement (sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString (1,json.name);
            ps.setInt (2,json.price);
            ps.setString (3,json.producer_id);
            ps.setTimestamp (4,new Timestamp (json.post_time));
            ps.setString (5,json.introduction);
            ps.setBoolean (6,json.auction);
            Blob blob =  Database.getInstance().getConn().createBlob();
            blob.setBytes(1, json.pic);
            ps.setBlob(7, blob);
            ps.executeUpdate ();
            var rs= ps.getGeneratedKeys ();
            int proid = 0;
            if(rs.next ()){
                proid = rs.getInt (1);
            }
            return new Message (0,0,Integer.toString (proid));
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        return null;
    }

    //购买普通商品
    public static Message buyNormalProduction(ProductionJson json){
        try {
            var  sql = "UPDATE production SET buyer_id=? WHERE production_id=?";
            var ps = Database.getInstance ().getConn ().prepareStatement (sql);
            ps.setString (1,json.buyer_id);
            ps.setInt (2,json.production_id);
            ps.executeUpdate ();
            return new Message(0,0,"购买成功");
        } catch (SQLException e) {
            e.printStackTrace ();
        }
        return null;
    }

    //竞拍商品
    public static Message buyAuctionProduction(ProductionJson json){
        try {
            var sql = "UPDATE production SET aution_max_price=?, max_price_user_id=? WHERE production_id=?";
            var ps = Database.getInstance ().getConn ().prepareStatement (sql);
            ps.setInt (1,json.auction_max_price);
            ps.setString (2,json.max_price_user_id);
            ps.setInt (3,json.production_id);
            ps.executeUpdate ();
            return new Message (0,0,"竞拍成功");
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        return null;
    }

    //删除商品
    public static Message deleteProduction(ProductionJson json){
        try {
            int proid = json.production_id;
            var sql = "DELETE * FROM production WHERE production_id=?";
            var ps = Database.getInstance ().getConn ().prepareStatement (sql);
            ps.setInt (1,proid);
            ;
            ps.executeUpdate ();
            return new Message(0,0,"商品删除成功");
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        return null;
    }
    //搜索商品
    public static List<ProductionJson> search(String keyword){
        try {
            var sql = "SELECT * FROM production WHERE name LIKE ? OR introduction LIKE ? ORDER BY post_time";
            var ps = Database.getInstance ().getConn ().prepareStatement (sql);
            ps.setString (1,keyword);
            ps.setString (2,keyword);
            var rs = ps.executeQuery ();
            List<ProductionJson> list  = new ArrayList<> ();
            while(rs.next ()){
                var pro = new ProductionJson ();
                pro.production_id = rs.getInt ("production_id");
                pro.name = rs.getString ("name");
                pro.price = rs.getInt ("price");
                pro.introduction = rs.getString ("introduction");
                pro.producer_id = rs.getString ("producer_id");
                pro.auction = rs.getBoolean ("auction");
                pro.auction_max_price  = rs.getInt("auction_max_price");
                pro.max_price_user_id = rs.getString ("max_price_user_id");
                pro.bought = rs.getBoolean ("bought");
                pro.buyer_id = rs.getString ("buyer_id");
                pro.post_time = rs.getTimestamp ("produce_tiem").getTime ();
                list.add (pro);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        return null;
    }

    //获取商品信息
    public static ProductionJson getProductionInfo(int proid){
        try {
            var sql = "SELECT * FROM production WHERE production_id=?";
            var ps = Database.getInstance ().getConn ().prepareStatement (sql);
            ps.setInt (1,proid);
            var rs = ps.executeQuery ();
            var pro = new ProductionJson ();
            if(rs.next ()){
                pro.production_id = rs.getInt ("production_id");
                pro.name = rs.getString ("name");
                pro.price = rs.getInt ("price");
                pro.introduction = rs.getString ("introduction");
                pro.producer_id = rs.getString ("producer_id");
                pro.auction = rs.getBoolean ("auction");
                pro.auction_max_price  = rs.getInt("auction_max_price");
                pro.max_price_user_id = rs.getString ("max_price_user_id");
                pro.bought = rs.getBoolean ("bought");
                pro.buyer_id = rs.getString ("buyer_id");
                pro.post_time = rs.getTimestamp ("post_time").getTime ();
            }
            return pro;
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        return null;
    }

    //获取我的商品
    public static List<ProductionJson> getMyProduction(String userid){
        try {
            var sql = "SELECT * FROM production WHERE producer_id=?";
            var ps = Database.getInstance ().getConn ().prepareStatement (sql);
            var rs = ps.executeQuery ();
            List<ProductionJson> list = new ArrayList<> ();
            while(rs.next ()){
                var pro = new ProductionJson ();
                pro.production_id = rs.getInt ("production_id");
                pro.name = rs.getString ("name");
                pro.price = rs.getInt ("price");
                pro.introduction = rs.getString ("introduction");
                pro.producer_id = rs.getString ("producer_id");
                pro.auction = rs.getBoolean ("auction");
                pro.auction_max_price  = rs.getInt("auction_max_price");
                pro.max_price_user_id = rs.getString ("max_price_user_id");
                pro.bought = rs.getBoolean ("bought");
                pro.buyer_id = rs.getString ("buyer_id");
                pro.post_time = rs.getTimestamp ("post_time").getTime ();
                list.add (pro);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace ( );
        }
        return null;
    }
}
