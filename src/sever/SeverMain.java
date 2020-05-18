package sever;

import sever.base.Database;
import sever.base.KSeverManager;

import java.io.IOException;
import java.sql.SQLException;

public class SeverMain {
    public static void main(String[] args) {
        System.out.println ("服务器已启动" );
        try {
            var database = new Database ();
            var severManager = new KSeverManager ();
        } catch (SQLException e) {
            e.printStackTrace ( );
        } catch (IOException e) {
            e.printStackTrace ( );
        }
    }
}
