package com.igreedy.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.igreedy.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @Auther: wuyandong
 * @Date: 2018/6/25 18:21
 * @Description:
 */
public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JedisPool jedisPool;

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    /**
     * protostuff 需要我们自己写schema，然后转化成一个文件来告诉序列化，但这样的方式
     * 很不友好，所以protostuff帮你动态做这些过程，而且性能几乎没有损失
     */
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    public Seckill getSeckill(long seckillId) {
        // redis操作逻辑
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                // jedis是一个对象。既然是对象就有序列化问题
                // 并没有实现内部序列化操作
                // byte[] 二进制数组，不管是java对象，python对象，文件，图片，一段文字
                // get -> byte[] -> 反序列化 -> object(Seckill)

                // protostuff 比 jdk的serializable, 压缩更下，也更快，节省了io时间和资源
                // 采用自定义序列化:就是把一个对象序列化为字节数组，传入到redis当中。
                // protostuff 实现步骤: 你告诉我你这个对象的class，然后我内部有一个类似于schema这个东西
                // 来描述你这个class是什么结构，但是你这个class必须有get,set方法的标准普通的java对象。
                // 而不是string，long这样类型

                // 下面的步骤就是：从redis中获取二进制数组，然后转换为实体对象。就是seckill.
                // 也就是 redis获取数据 -> byte[] -> object(Seckill)
                byte[] bytes = jedis.get(key.getBytes());
                //缓存重获渠道
                if (bytes != null) {
                    //空对象
                    Seckill seckill = schema.newMessage();
                    // 根据schema，将bytes的数据填入空对象seckill中去，使之有数据。
                    ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                    // seckill 被反序列化, 即被赋值了。
                    return seckill;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public String putSeckill(Seckill seckill) {

        // 序列化过程 object(Seckill) -> byte[] -> 发送给redis
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckill.getSeckillId();
                // 缓存器，对于数据量很大时候，能有一个缓冲的过程
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时缓存
                int timeout = 60 * 60; //1小时
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

}

