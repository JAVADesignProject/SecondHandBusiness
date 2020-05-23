package base.json;

import base.Parser;

public class Json {
    public String toString (){
        return Parser.toJson (this );
    }
}
