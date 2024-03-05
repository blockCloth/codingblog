package com.coding.blog.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @User Administrator
 * @CreateTime 2023/12/17 16:28
 * @className com.coding.blog.common.util.RedisTemplateUtil
 */
@Component
public class RedisTemplateUtil {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 判断key是否存在
     */
    public Boolean isExist(String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置字符串信息
     */
    public void set(String key,Object value){
        redisTemplate.opsForValue().set(key,value);
    }

    /**
     * 设置字符串信息以及过期时间
     */
    public void setExpire(String key, Object value, long time){
        redisTemplate.opsForValue().set(key,value);
        expire(key,time);
    }


    /**
     * 查询对应的value值
     * @param key
     * @return
     */
    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除字符串信息
     */
    public Boolean del(String key){
        return redisTemplate.delete(key);
    }

    /**
     * 批量删除缓存信息
     */
    public Long del(List<String> keys){
        return redisTemplate.delete(keys);
    }

    /**
     * 设置value的过期时间
     */
    public Boolean expire(String key, long time){
        return redisTemplate.expire(key,time, TimeUnit.SECONDS);
    }

    /**
     * 查询value剩余时间
     */
    public Long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 设置value值增长
     */
    public Long incr(String key,long delta){
        return redisTemplate.opsForValue().increment(key,delta);
    }

    /**
     * 设置value值减少
     */
    public Long decr(String key,long delta){
        return redisTemplate.opsForValue().decrement(key,delta);
    }

    /**
     * 设置hash结构value值
     */
    public void hSet(String key,Object hashField,Object value){
        redisTemplate.opsForHash().put(key,hashField,value);
    }

    /**
     * 设置hash结构value值，以及过期时间
     */
    public void hSetExpire(String key, Object hashField, Object value, long time){
        redisTemplate.opsForHash().put(key,hashField,value);
        expire(key,time);
    }

    /**
     * 直接设置完整hash结构
     */
    public void hSetAll(String key,Map<Object,Object> hashMap){
        redisTemplate.opsForHash().putAll(key,hashMap);
    }

    /**
     * 直接设置完整hash结构以及过期时间
     */
    public void hSetAll(String key,Map<Object,Object> hashMap,long time){
        redisTemplate.opsForHash().putAll(key,hashMap);
        expire(key,time);
    }

    /**
     * 获取hash结构的value值
     */
    public Object hGet(String key,Object hashField){
        return redisTemplate.opsForHash().get(key,hashField);
    }

    /**
     * 查看key集合中是否拥有Field字段
     */
    public boolean hExists(String key,Object hashField){
        return redisTemplate.opsForHash().hasKey(key,hashField);
    }

    /**
     * 获取key集合中的所有Field
     */
    public Map<Object, Object> hGetAll(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 删除key集合中的字段
     */
    public Long hDel(String key,Object...hashField){
        return redisTemplate.opsForHash().delete(key,hashField);
    }

    /**
     * 设置key中的Field的增量
     */
    public Long hIncr(String key,Object hashField,Long delta){
        return redisTemplate.opsForHash().increment(key,hashField,delta);
    }

    /**
     * 设置key中的Field的减量
     */
    public Long hDecr(String key,Object hashField,Long delta){
        return redisTemplate.opsForHash().increment(key,hashField,-delta);
    }

    /**
     * 将一个或者多个元素添加到集合中
     */
    public void sAdd(String key,Object...values){
        redisTemplate.opsForSet().add(key,values);
    }

    /**
     * 将一个或者多个元素添加到集合中以及设置过期时间
     */
    public void sAddExpire(String key,long time,Object...values){
        redisTemplate.opsForSet().add(key,values);
        expire(key,time);
    }

    /**
     * 取出集合中的所有数据
     */
    public Set<Object> sMembers(String key){
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 判断集合中是否包含该value值
     */
    public Boolean sIsMember(String key,Object value){
        return redisTemplate.opsForSet().isMember(key,value);
    }

    /**
     * 删除集合中一个或多个元素
     */
    public Long sRemove(String key,Object...values){
        return redisTemplate.opsForSet().remove(key,values);
    }

    /**
     * 获取集合中的元素个数
     * @param key
     * @return
     */
    public Long sSize(String key){
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 将集合中的一个值移动到另外一个集合
     */
    public Boolean sMove(String sourceKey,String destinationKey,Object value){
        return redisTemplate.opsForSet().move(sourceKey,value,destinationKey);
    }

    /**
     * 往集合中添加一个或多个数据
     */
    public Long lPush(String key,Object...values){
        return redisTemplate.opsForList().rightPushAll(key,values);
    }

    /**
     * 往集合中添加一个或多个数据以及设置过期时间
     */
    public Long lPushExpire(String key,long time,Object...values){
        expire(key, time);
        return redisTemplate.opsForList().rightPush(key,values);
    }

    /**
     * 根据下标获取元素（从左到右），（0，-1）表示获取所有
     */
    public List<Object> lRange(String key,long start,long end){
        return redisTemplate.opsForList().range(key,start,end);
    }

    /**
     * 获取集合长度
     */
    public Long lLength(String key){
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 根据索引下标获取元素
     */
    public Object lIndex(String key,long index){
        return redisTemplate.opsForList().index(key,index);
    }

    /**
     * 移除列表中的元素
     */
    public Long lRemove(String key,long count,Object value){
        return redisTemplate.opsForList().remove(key, count, value);
    }
}
