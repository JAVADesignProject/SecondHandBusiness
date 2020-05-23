package base;

import com.google.gson.*;
import com.google.gson.internal.Primitives;

import java.io.File;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Parser {
    public static <T> T fromJson(String json, Class<T> classOfT)throws JsonSyntaxException{
        return Primitives.wrap(classOfT).cast (new Gson().fromJson (json, classOfT));
    }

    public static <T> T fromJson(String json, Type typeOf){
        return new Gson().fromJson (json,typeOf);
    }

    public static String toJson(Object src){
        return new Gson().toJson (src);
    }

    public static String md5(String string){
        try {
            MessageDigest md5 =MessageDigest.getInstance ("MD5");
            md5.update (StandardCharsets.UTF_8.encode (string));
            return String.format ("%032x",new BigInteger (1,md5.digest ()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace ( );
            return null;
        }
    }

    public static String md5(File file){
        return "";
    }
}
