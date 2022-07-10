# 								Redis

##### 1. Redis缓存框架基本介绍

> Redis 是完全开源免费的，是一个高性能的key-value数据库，目前市面上主流的数据库
>
> Redis、Memcache、Tair(淘宝自研发)
>
> Redis的官网：https://redis.io/
>
> 内存数据库（nosql数据库）、mysql、sqlserver
>
> 关系数据库存放在硬盘中 查询实现io操作
>
> 非关系数据库 Redis 持久化机制 淘汰策略
>
> Jvm内置缓存框架 ECACH os cache

##### 2. Redis的应用场景

> 1. Token令牌的生成
> 2. 短信验证码Code
> 3. 缓存查询数据 (可以减轻数据库的访问压力)
> 4. 网页计数器
> 5. 分布式锁
> 6. 延迟操作

##### 3. Redis单线线程模型

> Redis的底层采用Nio中的多路IO复用的机制，能够非常好的支持这样的并发，从而保证线程安全问题；
>
> 所以在linux操作系统采用epoll实现事件驱动回调，不会存在空轮训的情况，只对活跃的 socket连接实现主动回调这样在性能上有大大的提升，所以时间复杂度是为o(1)
>
> **注意：windows操作系统是没有epoll，只有linux系统才有epoll**

##### 4. SpringBoot整合Redis

1：在Redis存放一个对象 使用json序列化与反序列化

2：直接使用redis自带序列化方式存储对象

##### 5. Redis和MySql数据一致性解决方案

1：直接清除Redis的缓存，重新读取数据库即可

2：使用mq异步订阅mysql binlog实现增量同步

3：使用alibaba的canal

##### 6.  Redis的持久化机制

> Redis在某种原因的情况下宕机，数据不会丢失，因为它的持久化。
>
> EHCACHE
>
> 大部分缓存框架都会基本功能：淘汰策略、持久化机制。
>
> Redis的持久化机制：aof、rdb(默认)

##### 7.全量同步与增量同步区别：

 1.就是每天定时(避开高放弃)或者是采用一种周期的实现将数据拷贝另外一个地方。

频率不是很大，但是可能会造成数据的丢失。

2. 增量同步采用行为操作对数据的实现同步

频率非常高、对服务器同步的压力也是非常大的、保证数据不丢失。

##### 8. RDB与AOF实现持久化的区别

> Redis提供了两种持久化的机制，分别为RDB、AOF实现，RDB采用定时（全量）持久化机制，但是服务器因为某种原因宕机后可能数据会丢失，AOF是基于数据日志操作实现的持久化，所以AOF采用增量同步的方案。
>
> Redis已经帮助我默认开启了rdb存储。

![image-20211013222730513](D:\软件\svn-测试连接文件夹\Java资料\图片\image-20211013222730513.png)

##### 9. Aof

> 在Redis的配置文件中存在三种同步方式，它们分别是：
>
> 1. appendfsync always   每次有数据修改发生时都会写入AOF文件，能够保证数据不丢失，但是效率非常低。它是一个个同步进去，来回访问次数太多造成效率低。
> 2. appendfsync everysec 每秒钟同步一次，可能会丢失1s内的数据，但是效率非常高。
> 3. appendfsync no     从不同步。高效但是数据不会被持久化。
>
> 直接修改redis.conf中 appendonly yes
>
> 建议最好还是使用everysec 既能够保证数据的同步、效率也还可以。

##### 10. Redis核心六大淘汰策略

> noeviction：当内存使用达到阈值的时候，执行命令直接报错 
>
> allkeys-lru：在所有的key中，优先移除最近未使用的key。(推荐)
>
> volatile-lru：在设置了过期时间的键空间中，优先移除最近未使用的key。
>
> allkeys-random：在所有的key中，随机移除某个key。
>
> volatile-random：在设置了过期时间的键空间中，随机移除某个key。
>
> volatile-ttl：在设置了过期时间的键空间中，具有更早过期时间的key优先移除。

##### 11. Redis的key过期回调监听

监听配置文件

```Java
@Configuration
public class RedisListenerConfig {
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }
}
```

接收过期key后的处理方法

```Java、
@Component
public class RedisExpirationListener extends KeyExpirationEventMessageListener {

    public RedisExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    /**
     * 使用该方法监听，当我们的Redis的key失效的时候会触发该方法
     *
     * @param message
     * @param pattern
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiraKey = message.toString();
        System.out.println("expira失效的key:" + expiraKey);
    }
}
```

##### 12. Redis事务操作

Multi 开启事务后，也能操作修改提交事务的对象

EXEC 提交事务

Watch 可以监听一个或者多个key，在提交事务之前是否有发生了变化 如果发生边了变化就不会提交事务，没有发生变化才可以提交事务 版本号码 乐观锁

watch name

multi

set name xiaoxiao

exec

Discard 取消提交事务

**注意：Redis**官方是没有提供回滚方法，只提供了取消事务。

##### 13. Redis实现分布式锁

什么是分布式锁？

本地锁：在多线程中，保证只有一个线程执行（保证线程的安全，线程的安全问题）

分布式锁：在分布式中，保证只有一个JVM执行（多个JVM执行安全问题）

> - Redis分布式锁实现思路：
> - Redis实现分布式锁基于SetNx命令，因为在Redis中key是保证是唯一的。所以当多个线程同时的创建setNx时，只要谁能够创建成功谁就能够获取到锁。
> - Set 命令 每次set时，可以修改原来旧值；
> - SetNx命令 每次SetNx检查该 key是否已经存在，如果已经存在的话不会执行任何操作。返回为0 如果已经不存在的话直接新增该key。
> - 1：新增key成功 0 失败
> - 获取锁的时候：当多个线程同时创建SetNx k，只要谁能够创建成功谁就能够获取到锁。
> - 释放锁：可以对该key设置一个有效期可以避免死锁的现象。



Redis 工具类代码

```java
public class RedisUtil {
    //protected static Logger logger = Logger.getLogger(RedisUtil.class);

    private static String IP = "101.132.65.100";

    //Redis的端口号
    private static int PORT = 6379;

    //可用连接实例的最大数目，默认值为8；
    //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
    private static int MAX_ACTIVE = 100;

    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 20;

    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
    private static int MAX_WAIT = 3000;

    private static int TIMEOUT = 3000;

    //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;

    //在return给pool时，是否提前进行validate操作；
    private static boolean TEST_ON_RETURN = true;

    private static JedisPool jedisPool = null;

    /**
     * redis过期时间,以秒为单位
     */
    public final static int EXRP_HOUR = 60 * 60; //一小时
    public final static int EXRP_DAY = 60 * 60 * 24; //一天
    public final static int EXRP_MONTH = 60 * 60 * 24 * 30; //一个月

    /**
     * 初始化Redis连接池
     */
    private static void initialPool() {
        try {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(MAX_ACTIVE);
            config.setMaxIdle(MAX_IDLE);
            config.setMaxWaitMillis(MAX_WAIT);
            config.setTestOnBorrow(TEST_ON_BORROW);

            jedisPool = new JedisPool(config, IP, PORT, TIMEOUT, "123456");
        } catch (Exception e) {
            //logger.error("First create JedisPool error : "+e);
            e.getMessage();
        }
    }


    /**
     * 在多线程环境同步初始化
     */
    private static synchronized void poolInit() {
        if (jedisPool == null) {
            initialPool();
        }
    }


    /**
     * 同步获取Jedis实例
     *
     * @return Jedis
     */
    public synchronized static Jedis getJedis() {
        if (jedisPool == null) {
            poolInit();
        }
        Jedis jedis = null;
        try {
            if (jedisPool != null) {
                jedis = jedisPool.getResource();
            }
        } catch (Exception e) {
            e.getMessage();
            // logger.error("Get jedis error : "+e);
        }
        return jedis;
    }


    /**
     * 释放jedis资源
     *
     * @param jedis
     */
    public static void returnResource(final Jedis jedis) {
        if (jedis != null && jedisPool != null) {
            jedisPool.returnResource(jedis);
        }
    }

    public static Long sadd(String key, String... members) {
        Jedis jedis = null;
        Long res = null;
        try {
            jedis = getJedis();
            res = jedis.sadd(key, members);
        } catch (Exception e) {
            //logger.error("sadd  error : "+e);
            e.getMessage();
        }
        return res;
    }
}
```

 工具类需要引入的依赖

```Java
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>2.9.0</version>
</dependency>
```

获取锁、释放锁相关代码

```Java
public class RedisLock {
    
    private static final int setnxSuccss = 1;

    /**
     * 获取锁
     *
     * @param lockKey        定义锁的key
     * @param notLockTimeOut 没有获取锁的超时时间
     * @param lockTimeOut    使用锁的超时时间
     * @return
     */

    public String getLock(String lockKey, int notLockTimeOut, int lockTimeOut) {
        // 获取Redis连接
        Jedis jedis = RedisUtil.getJedis();
        // 定义没有获取锁的超时时间
        Long endTimeOut = System.currentTimeMillis() + notLockTimeOut;
        while (System.currentTimeMillis() < endTimeOut) {
            String lockValue = UUID.randomUUID().toString();
            // 如果在多线程情况下谁能够setnx 成功返回0 谁就获取到锁
            if (jedis.setnx(lockKey, lockValue) == setnxSuccss) {
                jedis.expire(lockKey, lockTimeOut / 1000);
                return lockValue;
            }
            // 否则情况下 在超时时间内继续循环
        }
        try {
            if (jedis != null) {
                jedis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 释放锁
     *
     * @param lockKey   锁的 key 值
     * @param lockValue 锁的 value 值
     * @return
     */
    public boolean unLock(String lockKey, String lockValue) {
        // 获取 Redis 连接
        Jedis jedis = RedisUtil.getJedis();
        try {
            // 判断获取锁时保证自己删除自己
            if (lockValue.equals(jedis.get(lockKey))) {
                return jedis.del(lockKey) > 0 ? true : false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }
}
```

##### 14. Zookeeper 和 Redis 实现分布式锁的优缺点

> Zookeeper分布式锁实现思路
>
> 1.获取锁
>
> 多个不同的jvm在zk集群上创建一个相同的全局唯一的临时路径，只要谁能够创建成功谁就能够获取到锁。
>
>  分析：临时节点对我们节点设置有效期
>
> 2.释放锁
>
> 人为主动删除该节点或者使用Session有效期
>
> 3.超时锁（没有获取锁、已经获取锁）
>
> 等待获取锁的超时时间
>
> 已经获取到锁 锁的有效期 5s
>
> Redis也类似于zk事件通知
>
> Redis官网是有了一个redis解决分布锁框架redission
