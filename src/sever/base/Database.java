package sever.base;

import java.sql.*;

public class Database {

    private static Database instance;
    private String DATABASE = "jdbc:mysql://localhost:3306/trade?severTimezone=GMT%2B8";
    private String USERNAME = "root";
    private String PASSWORD = "12345678";

    public static Database getInstance(){
        return instance;
    }

    private Connection conn;
    private Statement stat;

    public Database() throws SQLException {
        instance=this;
        conn = DriverManager.getConnection (DATABASE,USERNAME,PASSWORD);
        stat = conn.createStatement ();
    }

    public Connection getConn() {
        return conn;
    }

    public Statement getStat() {
        return stat;
    }

    public int update(String sql)throws SQLException{
        return stat.executeUpdate (sql);
    }

    public ResultSet query(String sql)throws SQLException{
        return stat.executeQuery(sql);
    }

    public PreparedStatement preparedStatement(String sql)throws SQLException{
        return conn.prepareStatement (sql);
    }
}
