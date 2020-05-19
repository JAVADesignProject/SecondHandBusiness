package base.json;

import base.Parser;

public class MessageJson extends  Json{
    public String sender;//信息发送者
    public String receiver;//信息接受者
    public String text;//信息内容
    public int msgid;//信息id
    public long time;//发送时间
    //public int type;//是否已被接受

    public static MessageJson parse(String json){
        return Parser.fromJson (json, MessageJson.class);
    }
}
