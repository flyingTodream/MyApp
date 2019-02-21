package com.common.utils;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtUtils {
		/**
		 * 过期时间
		 */
	private static final long EXPIRE_TIME	 = 30 * 60 *1000;
	
		/**
		 * token私钥
		 */
	private static final String TOKEN_SECRET = "b99b47a3b83746e8896c95e624dd0328";
	
/**
 * 生成token
 * @param username
 * @param id
 * @return
 */
	public static String sign(String username , String id) {
		try {
			Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			Map<String, Object> header = new HashMap<>();
			header.put("typ", "JWT");
			header.put("alg", "HS256");
			return JWT.create().withHeader(header)
					.withClaim("username", username)
					.withClaim("id", id)
					.withExpiresAt(date)
					.sign(algorithm);
			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	
	/**
	 * 检验token是否正确
	 */
	public static boolean verify(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(token);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	/**
	 * 获取用户名
	 * @param token
	 * @return
	 */
	public static String getUsername(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("username").asString();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	/**
	 * 获取用户ID
	 * @param token
	 * @return
	 */
	public static String getId(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getClaim("id").asString();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public static void main(String[] args) {
		
		String str = "1,2,3";
		
		List<String> result = Arrays.asList(str.split(","));
		System.out.println(result.get(1));
		//System.out.println(sign("11","22"));
		//System.out.println(sign("11","22"));
	}
}
