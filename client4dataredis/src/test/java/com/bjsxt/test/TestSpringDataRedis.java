package com.bjsxt.test;


import com.bjsxt.dataredis.SpringDataRedisApp;
import com.bjsxt.dataredis.pojo.User;
import org.junit.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * spring data redis，提供的RedisTemplate，对key和value给予了不同的，可配置的序列化器
 * 默认使用JDK的Serializable序列化器。 会把Java对象通过Serializable序列化,并操作。
 *
 * 对于应用来说，JDK的序列化器有不可忽视的缺陷。
 * 1、容量大
 * 2、效率低
 *
 * 一般来说，key一定是字符串。没必要序列化。直接操作即可。
 * value可以是任意的Java对象，Java对象，转换成字符串，最好使用JSON格式描述。
 *
 *
 * 通过自定义RedisTemplate对象的创建，实现不同的序列化规则。
 */

@SpringBootTest(classes = {SpringDataRedisApp.class})
@RunWith(SpringRunner.class)
public class TestSpringDataRedis {
   //spring data redis默认连接localhost:6379
   @Autowired
   private RedisTemplate<String,Object> redisTemplate;


   @Test
   public void testHash(){
      redisTemplate.opsForHash().put("data-stu","name","bjsxt");
   }


   /**
    * 面向各种数据类型的访问操作，基本都是基于redis命令开发的。
    * 如:  opsForValue().set()  .get()
    * 面向key和各种类型数据的特殊操作，基本都是基于面向对象的开发思想定义命名的。
    * 符合程序员的开发习惯，如: getExpire 相当于ttl命令
    */

   @Test
   public void testExpire(){
      //设置有效期
      redisTemplate.expire("data-redis-key",10, TimeUnit.MINUTES);
      long times = redisTemplate.getExpire("data-redis-key");
      System.out.println(times);
   }


   /**
     * spring data redis,所有操作都有二次封装
     * 如： key操作,在RedisTemplate中直接调用方法。ttl,expire,del
     * 如:  字符串操作，在对应得operator中调用方法，需要通过Template获取Operator
     *     template.opsForValue - 字符串
     *     template.opsForList  - List链表
     */
   @Test
    public void testSet(){
       User user = new User();
       user.setId(10);
       user.setName("张三");
       user.setPassword("123");
       redisTemplate.opsForValue().set("data-redis-key",user);
   }

   @Test
   public void testGet(){
      System.out.println(
              redisTemplate.opsForValue().get("data-redis-key")
      );
   }




}
