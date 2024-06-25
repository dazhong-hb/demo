package com.lwz.demo.common.utils.support;

import cn.hutool.json.JSONUtil;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class StringRedisUtils {

    /**
     * 系统附件属性
     */
    private static final StringRedisTemplate stringRedisTemplate;

    /** 从Spring容器中加载UploadProperties类 */
    static {
        stringRedisTemplate = SpringUtil.getBean(StringRedisTemplate.class);
    }

    /**
     * 用 value 参数覆写(overwrite)给定 key 所储存的字符串值，从偏移量 offset 开始
     *
     * @param key    键
     * @param value  值
     * @param offset offset
     */
    public static void save(String key, Object value, long offset) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), offset);
    }

    /**
     * Redis通过时间去存储
     *
     * @param key     键
     * @param value   值
     * @param timeout 数字几，比如（1,2,3）
     * @param unit    代表的是分钟还是小时还是小时（TimeUnit.DAYS：带表的是天）
     */
    public static void save(String key, Object value, long timeout, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(value), timeout, unit);
    }

    /**
     * 设置新值时先判断该值原来是否存在
     *
     * @param key   键
     * @param value 值
     * @return Boolean (true:说明不存在，false：说明存在)
     */
    public static Boolean saveIfAbsent(String key, Object value) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, JSONUtil.toJsonStr(value));
    }

    /**
     * 获取key对应的值
     *
     * @param key key
     */
    public static String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 设置新的值并返回其旧值
     *
     * @param key   键
     * @param value 值
     * @return String
     */
    public static String getAndSet(String key, Object value) {
        return stringRedisTemplate.opsForValue().getAndSet(key, JSONUtil.toJsonStr(value));
    }

    /**
     * 为多个键分别设置它们的值
     *
     * @param map map
     */
    public static void multiSet(Map<? extends String, ? extends String> map) {
        stringRedisTemplate.opsForValue().multiSet(map);
    }

    /**
     * 为多个键分别设置它们的值，如果存在则返回false，不存在返回true
     *
     * @param map map
     */
    public static Boolean multiSetIfAbsent(Map<? extends String, ? extends String> map) {
        return stringRedisTemplate.opsForValue().multiSetIfAbsent(map);
    }

    /**
     * 获取key对应的值
     *
     * @param key key
     * @return
     */
    public static List<String> gets(String key) {
        List<String> keyList = new ArrayList<>();
        Set<String> keys = stringRedisTemplate.keys(key);
        if (keys != null) {
            keyList = keys.stream().parallel().collect(Collectors.toList());
            return getAndSet(keyList);
        }
        return keyList;
    }

    /**
     * 模糊获取key集合
     *
     * @param key
     * @return
     */
    public static Set<String> keys(String key) {
        return stringRedisTemplate.keys(key);
    }


    /**
     * 根据key获取过期时间
     *
     * @param key key
     * @return Long
     */
    public static Long getExpire(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    /**
     * 根据key获取过期时间并换算成指定单位
     *
     * @param key  key
     * @param unit unit
     * @return Long
     */
    public static Long getExpire(String key, TimeUnit unit) {
        return stringRedisTemplate.getExpire(key, unit);
    }

    /**
     * 为多个键分别取出它们的值
     *
     * @param list list
     * @return List<String>
     */
    public static List<String> getAndSet(List<String> list) {
        return stringRedisTemplate.opsForValue().multiGet(list);
    }

    /**
     * 增量，支持整行
     *
     * @param key   key
     * @param delta delta
     */
    public static Long incrementLong(String key, Long delta) {
        return stringRedisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 增量，支持整行
     *
     * @param key   key
     * @param delta delta
     */
    public static Long increment(String key, Integer delta) {
        return stringRedisTemplate.boundValueOps(key).increment(delta);
    }

    /**
     * 增量，支持浮点型
     *
     * @param key   key
     * @param delta delta
     */
    public static void increment(String key, double delta) {
        stringRedisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 设置过期时间
     *
     * @param key     key
     * @param timeout timeout
     * @param unit    unit
     */
    public static void expire(String key, Long timeout, TimeUnit unit) {
        stringRedisTemplate.expire(key, timeout, unit);
    }

    /**
     * 删除指定的key
     *
     * @param key key
     */
    public static Boolean delete(String key) {
        Boolean delete = stringRedisTemplate.delete(key);
        return delete == null ? false : delete;
    }

    /**
     * 批量删除
     *
     * @param keys
     */
    public static void deletes(Set<String> keys) {
        stringRedisTemplate.delete(keys);
    }

    /**
     * 检查key是否存在，返回boolean值
     *
     * @param key key
     * @return 存在返回true否则相反
     */
    public static Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }

}
