package base;

public class Message {
    public int code;//通信代码，确定消息类型
    public int token;//附加值，如有必要，用于验证身份
    public String props;//此条消息附带的结果或者参数  格式为Json
    public Message(){
        this(0, -1,"");
    }

    public Message(int code,String props){
        this.code=code;
        this.props=props;
    }

    public Message(int code, int token, String props) {
        this.code = code;
        this.token = token;
        this.props = props;
    }

    public String toString(){
        return Praser.toJson (this);
    }

    public static Message prase(String json){
        return Praser.fromJson (json,Message.class);
    }
}
