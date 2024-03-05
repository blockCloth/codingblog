package com.coding.blog.common.util;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @User Administrator
 * @CreateTime 2023/12/5 17:11
 * @className com.coding.blog.common.util.JwtTokenUtil
 */
@Slf4j
public class JwtTokenUtil {

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "create";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 根据用户信息生成token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 根据用户名，创建时间生成JWT的token
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .addClaims(claims) // 设置payload
                .setExpiration(generateExpirationDate()) // 设置过期时间
                .signWith(SignatureAlgorithm.HS512, secret) // 设置签名
                .compact();
    }

    /**
     * 设置过期时间
     *
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration);
    }

    /**
     * 从token中获取过期时间
     */
    public Date getExpirationFromToken(String token) {
        return getExpiredDateFromToken(token);
    }

    /**
     * 从token中获取登录用户名
     */
    public String getUserNameFromToken(String token) {
        String username = null;
        Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            username = claims.getSubject();
        }
        return username;
    }

    /**
     * 从token中获取JWT的负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser() // 解析token
                    .setSigningKey(secret) // 设置签名
                    .parseClaimsJws(token) // 获取负载
                    .getBody();
        } catch (Exception e) {
            log.warn("JWT格式验证失败:{}", token);

        }
        return claims;
    }

    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 刷新token
     */
    public String refreshToken(String oldToken) {
        if (StrUtil.isEmpty(oldToken)) return null;
        // 获取到token值
        String token = oldToken.substring(tokenHead.length());
        if (StrUtil.isEmpty(token)) return null;

        Claims claims = getClaimsFromToken(token);
        //判断token校验是否通过
        if (claims == null) return null;
        //重新刷新token
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

}
