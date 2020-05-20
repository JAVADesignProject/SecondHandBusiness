package base;

import java.util.Calendar;

public class KClass {
    public static final String HOST = "localhost";//主机地址
    public static final int INFO_PORT = 5689;//信息服务端口（注册、登录、查询商品、购买商品）
    public static final int CHAT_PORT = 5690;//聊天端口
    public static final int FILE_PORT = 5691;//文件传输端口，处要用于传输商品图片

    //消息定义
    public static final int LOGIN = 0;//登录
    public static final int REGISTER = 1;//注册
    public static final int USER_INFO =2;//获取用户信息
    public static final int PRODUCTION_INFO = 3;//获取商品信息
    public static final int SEARCH = 4;//搜索
    public static final int NORMAL_PRODUCTION_BUY = 5;//购买普通商品
    public static final int UPDATE_PASSWORD = 6;//修改密码
    //public static final int UPDATE_NAME = 7;//修改用户名
    public static final int CHAT_MSG = 8;//获取聊天内容
    public static final int ADD_PRODUCTION = 9;//添加商品
    public static final int DELETE_PRODUCTION = 10;//删除商品
    public static final int AUCTION_PRODUCTION_BUY = 11;//购买拍卖商品
    public static final int MY_PRODUCTION = 12;//我的商品
    public static final int GET_COMMENT = 13;//获取评论
    public static final int ADD_COMMENT = 14;//添加评论
}
