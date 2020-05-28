package client.utils;

import base.json.ProductionJson;
import base.json.UserJson;

import java.util.List;

public class CurrentUser {
    public static String userId;//用户id
    public static String targetId;//聊天对象用户id
    public static String password;//密码
    public static String username;//用户名
    public static int status;//登录状态，是否已经登录
    public static List<UserJson> targetUsers;//聊天对象
    public static List<ProductionJson> productions;//应显示出来的商品
    public static int follow;//关注者数目
    public static int collection = 0;//收藏夹数目
}
