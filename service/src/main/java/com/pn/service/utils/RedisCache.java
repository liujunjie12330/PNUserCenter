package com.pn.service.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author javadadi
 * redis工具类
 */
@Component
@Slf4j
public class RedisCache {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /*普通操作*/

    /**
     * 指定key过期时间
     *
     * @param redisKey
     * @param time
     * @return
     */
    public boolean expire(String redisKey, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(redisKey, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param redisKey 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String redisKey) {
        return redisTemplate.getExpire(redisKey, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param redisKey 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String redisKey) {
        try {
            return redisTemplate.hasKey(redisKey);
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param redisKey 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void del(String... redisKey) {
        if (redisKey != null && redisKey.length > 0) {
            if (redisKey.length == 1) {
                redisTemplate.delete(redisKey[0]);
            } else {
                redisTemplate.delete(Arrays.asList(redisKey));
            }
        }
    }

    /*String 操作*/

    /**
     * 普通缓存获取
     *
     * @param redisKey 键
     * @return 值
     */
    public Object get(String redisKey) {
        return redisKey == null ? null : redisTemplate.opsForValue().get(redisKey);
    }

    /**
     * 普通缓存放入
     *
     * @param redisKey 键
     * @param value    值
     * @return true成功 false失败
     */
    public boolean set(String redisKey, Object value) {
        try {
            redisTemplate.opsForValue().set(redisKey, value);
            return true;
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param redisKey 键
     * @param value    值
     * @param time     时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String redisKey, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(redisKey, value, time, TimeUnit.SECONDS);
            } else {
                set(redisKey, value);
            }
            return true;
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return false;
        }
    }

    /**
     * 递增 适用场景： https://blog.csdn.net/y_y_y_k_k_k_k/article/details/79218254 高并发生成订单号，秒杀类的业务逻辑等。。
     *
     * @param redisKey 键
     * @param delta    要增加几(大于0)
     * @return
     */
    public long incr(String redisKey, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(redisKey, delta);
    }

    /**
     * 递减
     *
     * @param redisKey 键
     * @param delta       要减少几(小于0)
     * @return
     */
    public long decr(String redisKey, long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(redisKey, -delta);
    }

    /*hash操作*/

    /**
     * HashGet
     *
     * @param redisKey 键 不能为null
     * @param item     项 不能为null
     * @return 值
     */
    public Object getHashCache(String redisKey, String item) {
        return redisTemplate.opsForHash().get(redisKey, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param redisKey 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> getHashCaches(String redisKey) {
        return redisTemplate.opsForHash().entries(redisKey);
    }

    /**
     * HashSet
     *
     * @param redisKey 键
     * @param map      对应多个键值
     * @return true 成功 false 失败
     */
    public boolean setHashCaches(String redisKey, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(redisKey, map);
            return true;
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return false;
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param redisKey 键
     * @param map      对应多个键值
     * @param time     时间(秒)
     * @return true成功 false失败
     */
    public boolean setHashCache(String redisKey, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(redisKey, map);
            if (time > 0) {
                expire(redisKey, time);
            }
            return true;
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param redisKey 键
     * @param item     项
     * @param value    值
     * @return true 成功 false失败
     */
    public boolean setHashCache(String redisKey, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(redisKey, item, value);
            return true;
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param redisKey 键
     * @param item     项
     * @param value    值
     * @param time     时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean setHashCache(String redisKey, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(redisKey, item, value);
            if (time > 0) {
                expire(redisKey, time);
            }
            return true;
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param redisKey 键 不能为null
     * @param item     项 可以使多个 不能为null
     */
    public void delHashCache(String redisKey, Object... item) {
        redisTemplate.opsForHash().delete(redisKey, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param redisKey 键
     * @param item     项
     * @param by       要增加几(大于0)
     * @return
     */
    public double incrHash(String redisKey, String item, double by) {
        return redisTemplate.opsForHash().increment(redisKey, item, by);
    }

    /**
     * hash递减
     *
     * @param redisKey 键
     * @param item     项
     * @param by       要减少记(小于0)
     * @return
     */
    public double decrHash(String redisKey, String item, double by) {
        return redisTemplate.opsForHash().increment(redisKey, item, -by);
    }

    /*set操作*/

    /**
     * 根据key获取Set中的所有值
     *
     * @param redisKey 键
     * @return
     */
    public Set<Object> getSetCache(String redisKey) {
        try {
            return redisTemplate.opsForSet().members(redisKey);
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param redisKey 键
     * @param value    值
     * @return true 存在 false不存在
     */
    public boolean hasSetKey(String redisKey, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(redisKey, value);
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param redisKey 键
     * @param values   值 可以是多个
     * @return 成功个数
     */
    public long setSetCache(String redisKey, Object... values) {
        try {
            return redisTemplate.opsForSet().add(redisKey, values);
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param redisKey 键
     * @param time     时间(秒)
     * @param values   值 可以是多个
     * @return 成功个数
     */
    public long setSetCache(String redisKey, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(redisKey, values);
            if (time > 0)
                expire(redisKey, time);
            return count;
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param redisKey 键
     * @return
     */
    public long getSetCacheSize(String redisKey) {
        try {
            return redisTemplate.opsForSet().size(redisKey);
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param redisKey 键
     * @param values   值 可以是多个
     * @return 移除的个数
     */
    public long delSetCache(String redisKey, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(redisKey, values);
            return count;
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return 0;
        }
    }

    /*zset操作*/

    /**
     * 根据key获取Set中的所有值
     *
     * @param redisKey 键
     * @return
     */
    public Set<Object> getZetCaches(String redisKey) {
        try {
            return redisTemplate.opsForSet().members(redisKey);
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param redisKey 键
     * @param value    值
     * @return true 存在 false不存在
     */
    public boolean hasZsetKey(String redisKey, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(redisKey, value);
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return false;
        }
    }

    /**
     * 向指定key中添加元素，按照score值由小到大进行排列
     *
     * @param redisKey
     * @param value
     * @param score
     * @return
     */
    public Boolean setZsetCache(String redisKey, Object value, double score) {
        try {
            return redisTemplate.opsForZSet().add(redisKey, value, score);
        } catch (Exception e) {
            log.error(redisKey, e);
            return false;
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param redisKey 键
     * @param time     时间(秒)
     * @param values   值 可以是多个
     * @return 成功个数
     */
    public long setZsetCache(String redisKey, long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(redisKey, values);
            if (time > 0)
                expire(redisKey, time);
            return count;
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param redisKey 键
     * @return
     */
    public long getZsetCacheSize(String redisKey) {
        try {
            return redisTemplate.opsForSet().size(redisKey);
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param redisKey 键
     * @param values   值 可以是多个
     * @return 移除的个数
     */
    public long delZsetCache(String redisKey, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().remove(redisKey, values);
            return count;
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return 0;
        }
    }
    /*list操作*/

    /**
     * 获取list缓存的内容
     *
     * @param redisKey 键
     * @param start    开始 0 是第一个元素
     * @param end      结束 -1代表所有值
     * @return
     * @取出来的元素 总数 end-start+1
     */
    public List<Object> getListCache(String redisKey, long start, long end) {
        try {
            return redisTemplate.opsForList().range(redisKey, start, end);
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param redisKey 键
     * @return
     */
    public long getListCacheSize(String redisKey) {
        try {
            return redisTemplate.opsForList().size(redisKey);
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param redisKey 键
     * @param index    索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public Object getListCacheByIndex(String redisKey, long index) {
        try {
            return redisTemplate.opsForList().index(redisKey, index);
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param redisKey 键
     * @param value    值
     * @param time     时间(秒)
     * @return
     */
    public boolean setListCache(String redisKey, Object value) {
        try {
            redisTemplate.opsForList().rightPush(redisKey, value);
            return true;
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param redisKey 键
     * @param value    值
     * @param time     时间(秒)
     * @return
     */
    public boolean setListCache(String redisKey, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(redisKey, value);
            if (time > 0)
                expire(redisKey, time);
            return true;
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param time     时间(秒)
     * @param redisKey 键
     * @param value    值
     * @return
     */
    public boolean setListCache(String redisKey, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(redisKey, value);
            return true;
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param redisKey 键
     * @param value    值
     * @param time     时间(秒)
     * @return
     */
    public boolean setListCache(String redisKey, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(redisKey, value);
            if (time > 0)
                expire(redisKey, time);
            return true;
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param redisKey 键
     * @param index    索引
     * @param value    值
     * @return
     */
    public boolean updateListCacheByIndex(String redisKey, long index, Object value) {
        try {
            redisTemplate.opsForList().set(redisKey, index, value);
            return true;
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param redisKey 键
     * @param count    移除多少个
     * @param value    值
     * @return 移除的个数
     */
    public long delListCache(String redisKey, long count, Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(redisKey, count, value);
            return remove;
        } catch (Exception e) {
            log.error(redisKey, e.getMessage());
            return 0;
        }
    }
}
