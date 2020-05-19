package sever.base;

import base.KClass;
import sever.sever.KChatSever;
import sever.sever.KInfoSever;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KSeverManager {
    private static Map<Integer,String> tokens = new HashMap<> ();

    public KSeverManager() throws IOException {
        System.out.println ("数据库已连接" );
        var chatSever = new KChatSever (KClass.CHAT_PORT);
        new Thread (chatSever::update).start ();
        var infoSever = new KInfoSever (KClass.INFO_PORT);
        new Thread(infoSever::update).start ();
    }

    public static boolean containsToken(int key){
        return tokens.containsKey (key);
    }

    public static String getUserId(int key){
        return tokens.get (key);
    }

    public static void addToken(int token,String userid){
        tokens.put (token, userid);
    }

    public static void removeToken(String key){
        tokens.remove (key);
    }
}
