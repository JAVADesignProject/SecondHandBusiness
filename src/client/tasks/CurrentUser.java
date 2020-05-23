package client.tasks;

import base.json.UserJson;

import java.util.List;

public class CurrentUser {
    public static String userId;//用户id
    public static String targetId;//聊天对象用户id
    public static String password;//密码
    public static String username;//用户名
    public static int status;//登录状态，是否已经登录
    public static List<UserJson> targetUsers;//聊天对象

}
