package server;

import server.base.Database;
import server.base.KServerManager;

import java.io.IOException;
import java.sql.SQLException;

public class ServerMain {
    public static void main(String[] args) {
        System.out.println ("服务器已启动" );
        try {
            var database = new Database ();
            var severManager = new KServerManager();
        } catch (SQLException e) {
            e.printStackTrace ( );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }
}
