package com.bjsxt;


import org.junit.Test;
import redis.clients.jedis.*;


import java.util.HashSet;
import java.util.Set;

public class TestJedis {

    /**
     * 访问Redis Cluster
     * 所有的Jedis访问Redis Cluster的客户端对象,都是基于连接池的。
     * 不提供连接池配置,客户端中有默认的连接池配置。
     */
    @Test
    public void testJedisCluster() {
        //集群节点配置集合
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(
                new HostAndPort("192.168.112.128", 7001)
        );
        nodes.add(
                new HostAndPort("192.168.112.128", 7002)
        );
        nodes.add(
                new HostAndPort("192.168.112.128", 7003)
        );
        nodes.add(
                new HostAndPort("192.168.112.128", 7004)
        );
        nodes.add(
                new HostAndPort("192.168.112.128", 7005)
        );
        nodes.add(
                new HostAndPort("192.168.112.128", 7006)
        );

        //增加连接池的配置信息
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(10);
        config.setMinIdle(2);


        //创建Jedis连接Redis Cluster的客户端。构建的时候,必须传递Cluster的所有节点地址。
        //Cluster节点地址使用Set集合维护
        JedisCluster cluster = new JedisCluster(nodes,config);

        //cluster中的所有api和redis-cli中的所有命令一致。但是，不支持mset和mget操作
        //cluster.set("cluster-key","cluster-value");
        //cluster.mset("k1","v1","k2","v2","k3","v3");


    }


    /**
     * Jedis客户端，也是一个远程连接，类似JDBC中的Connection。
     * 也有池化概念。
     * Jedis的池化概念是基于GenericPool实现的。
     * 基于连接池的访问
     */
    @Test
    public void testJedisPooL(){
        // 创建一个连接池的配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        // 连接池配置
        config.setMaxTotal(10);  //最大连接
        config.setMaxIdle(10);   //最大活动链接
        config.setMinIdle(2);    //最小活动连接

        //基于连接池配置,创建连接池对象。
        JedisPool pool = new JedisPool(config,"192.168.112.128",6379);

        //从连接池中获取连接对象
        Jedis jedis = pool.getResource();

        System.out.println(jedis.get("jedis-key"));


    }




    /**
     * 测试访问Redis单机版
     * 使用基础连接方式,访问Redis。
     */
    @Test
    public void testJedisSingleton(){
      // Jedis， 提供了客户端类型，就是Jedis
      Jedis jedis = new Jedis("192.168.112.128",6379);
      //所有的Jedis API都是基于redis客户端命令定义的。也就是redis-cli控制台中的所有可执行命令
      //如: 新增字符串数据,命令是set key value。 Jedis API是 jedis.set(String key,String value)
      jedis.set("jedis-key","jedis-value");

      String value = jedis.get("name");
      System.out.println(value);



    }
}
