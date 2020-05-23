package base.json;

import base.Parser;

public class UserJson extends Json {
    public String userID;//用户id
    public String targetID;
    public String password;//密码
    public String username;//用户名
    public int status;//登录状态，是否已经登录

    public static UserJson parse(String json){
        return Parser.fromJson (json,UserJson.class);
    }

    public UserJson() {   }

    public UserJson(String userID) {
        this.userID = userID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof UserJson) {
            UserJson u = (UserJson)o;
            return userID.equals(u.userID);
        }
        return false;
    }

}
