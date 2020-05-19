package base.json;

import base.Parser;

public class UserJson extends Json {
    public String userid;//用户id
    public String password;//密码
    public String username;//用户名
    public int status;//登录状态，是否已经登录

    public static UserJson parse(String json){
        return Parser.fromJson (json,UserJson.class);
    }

    public UserJson() {   }

    public UserJson(String userid) {
        this.userid = userid;
    }

}
