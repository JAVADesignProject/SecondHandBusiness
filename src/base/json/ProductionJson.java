package base.json;

import base.Parser;


public class ProductionJson extends Json {
    public int production_id;//商品id
    public int price;//价格
    public String name;//名字
    public String introduction;//介绍
    public String producer_id;//发布者id
    public String buyer_id;//购买者id
    public long post_time;//发布时间
    public boolean bought;//是否被购买
    public boolean auction;//是否为拍卖商品，true为拍卖商品，false为普通商品
    public int auction_max_price;//拍卖最高价格
    public String max_price_user_id;//出价最高人的id
    public byte[] pic;//商品图片
    //public long length;//商品图片大小

    public static ProductionJson parse(String json){
        return Parser.fromJson (json, ProductionJson.class);
    }
}
