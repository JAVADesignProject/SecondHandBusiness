package base.json;

import base.Parser;

public class ProductionJson extends Json {
    public int production_id;//商品id
    public int price;//价格
    public String name;//名字
    public String introduction;//介绍
    public int producer_id;//发布者id
    public long produce_time;//发布时间
    public boolean bought;//是否被购买
    public boolean auction;//是否为拍卖商品
    public int auction_max_price;//拍卖最高价格
    public int max_price_user_id;//出价最高人的id


    public static ProductionJson parse(String json){
        return Parser.fromJson (json, ProductionJson.class);
    }
}
