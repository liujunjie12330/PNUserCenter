package com.pn.service.utils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;

import com.pn.common.constant.PNUserCenterConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *@author:Tlimited
 */
public class JWTUtil {

    /**
     * 生成签名，15分钟过期
     * 根据内部改造，支持6中类型，Integer,Long,Boolean,Double,String,Date
     * @param map
     * @return
     */
    public static String sign(Map<String, String> map) {
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + PNUserCenterConstant.JWT_EXPIRE_TIME);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(PNUserCenterConstant.JWT_SECRET_KEY);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "jwt");
            // 返回token字符串
            JWTCreator.Builder builder =  JWT.create()
                    .withHeader(header)
                    .withIssuedAt(new Date())
                    .withExpiresAt(date)
                    .withClaim("map",map);
            return builder.sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 检验token是否正确
     * @param **token**
     * @return
     */
    public static boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(PNUserCenterConstant.JWT_SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     *获取用户自定义Claim集合
     * @param token
     * @return
     */
    public static Map<String, Claim> getClaims(String token){
        Algorithm algorithm = Algorithm.HMAC256(PNUserCenterConstant.JWT_SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm).build();
        Map<String, Claim> jwt = verifier.verify(token).getClaims();
        return jwt;
    }

    /**
     * 获取过期时间
     * @param token
     * @return
     */
    public static Date getExpiresAt(String token){
        Algorithm algorithm = Algorithm.HMAC256(PNUserCenterConstant.JWT_SECRET_KEY);
        return  JWT.require(algorithm).build().verify(token).getExpiresAt();
    }

    /**
     * 获取jwt发布时间
     */
    public static Date getIssuedAt(String token){
        Algorithm algorithm = Algorithm.HMAC256(PNUserCenterConstant.JWT_SECRET_KEY);
        return  JWT.require(algorithm).build().verify(token).getIssuedAt();
    }

    /**
     * 验证token是否失效
     * @param token
     * @return true:过期   false:没过期
     */
    public static boolean isExpired(String token) {
        try {
            final Date expiration = getExpiresAt(token);
            return expiration.before(new Date());
        }catch (TokenExpiredException e) {
            return true;
        }

    }

    /**
     * 直接Base64解密获取header内容
     * @param token
     * @return
     */
    public static String getHeaderByBase64(String token){
        if (StringUtils.isEmpty(token)){
            return null;
        }else {
            byte[] header_byte = Base64.getDecoder().decode(token.split("\\.")[0]);
            String header = new String(header_byte);
            return header;
        }

    }

    /**
     * 直接Base64解密获取payload内容
     * @param token
     * @return
     */
    public static String getPayloadByBase64(String token){

        if (StringUtils.isEmpty(token)){
            return null;
        }else {
            byte[] payload_byte = Base64.getDecoder().decode(token.split("\\.")[1]);
            String payload = new String(payload_byte);
            return payload;
        }

    }

}


