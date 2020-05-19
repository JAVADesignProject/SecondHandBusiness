package base.json;

import base.Parser;

public class CommentJson extends Json {
    public int comment_id;//这条评论的id
    public int reviewer_id;//评论人的id
    public int Production_id;//被评论的商品id
    public String text;//评论内容
    public long time;//评论时间

    public static CommentJson parse(String json){
        return Parser.fromJson (json,CommentJson.class);
    }
}
