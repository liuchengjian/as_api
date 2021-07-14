package com.liucj.as.api.utils;

import com.liucj.as.api.entity.UserEntity;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserRedisUtil {
    private static final String BOARDING_PASS = "boarding_pass";

    /**
     * 添加用户到redis
     *
     * @param redisTemplate
     * @param session
     * @param userEntity
     */
    public static void addUser(StringRedisTemplate redisTemplate, HttpSession session, UserEntity userEntity) {
        redisTemplate.opsForValue().set(getKey(session), JsonUtil.toJsonString(userEntity));
    }

    /**
     * 检查用户是否在redis上存在，用于验证登录
     * @param redisTemplate
     * @param request
     * @return
     */
    public static boolean checkUser(StringRedisTemplate redisTemplate, HttpServletRequest request) {
        String val = redisTemplate.opsForValue().get(getBoardingPass(request));
        return val != null;
    }

    /**
     * 移除redis的用户
     *
     * @param redisTemplate
     * @param session
     */
    public static void removeUser(StringRedisTemplate redisTemplate, HttpSession session) {
        session.invalidate();
        redisTemplate.delete(getKey(session));
    }

    /**
     * 查找redis的用户
     *
     * @param redisTemplate
     * @param request
     * @return
     */
    public static UserEntity getUser(StringRedisTemplate redisTemplate, HttpServletRequest request) {
        String val = redisTemplate.opsForValue().get(getBoardingPass(request));
        if (val != null) {
            return JsonUtil.fromJson(val, UserEntity.class);
        }
        return null;
    }


    public static String getKey(HttpSession session) {
        return session.getId();
    }

    public static String getBoardingPass(HttpServletRequest request) {
        String pass = request.getHeader(BOARDING_PASS);
        return pass != null ? pass : "no-pass";
    }
}
