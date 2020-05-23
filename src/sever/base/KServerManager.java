package sever.base;

import base.KClass;
import sever.sever.KChatSever;
import sever.sever.KInfoSever;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class KServerManager {
    private static Map<Integer, String> tokens = new HashMap<>();

    public KServerManager() throws IOException {
        System.out.println("数据库已连接");
        var chatServer = new KChatSever(KClass.CHAT_PORT);
        new Thread(chatServer::update).start();
        System.out.println("聊天服务已启动");
//        var fileServer = new GFileServer(KClass.FILE_PORT);
//        new Thread(fileServer::update).start();
//        System.out.println("文件服务已启动");
        // 启动信息传输服务
        var infoServer = new KInfoSever(KClass.INFO_PORT);
        new Thread(infoServer::update).start();
        System.out.println("信息服务已启动");
    }

    public static boolean containsToken(int key) {
        return tokens.containsKey(key);
    }

    public static String getUserId(int key) {
        return tokens.get(key);
    }

    public static void addToken(int token, String userid) {
        tokens.put(token, userid);
    }

    public static void removeToken(String key) {
        tokens.remove(key);
    }
}
