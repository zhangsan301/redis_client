package com.bjsxt.dataredis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class MyRedisTemplateConfiguration {
    //增加序列化器配合
    //RedisTemplate只是一个客户端模板操作对象，不是连接对象。所以需要提供连接工厂。否则Template无法访问Redis
    //连接工厂，spring-boot-starter-data-redis自动创建，类型是RedisConnectionFactory
   @Bean
   public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
       RedisTemplate<String,Object> redisTemplate = new RedisTemplate<String, Object>();
       //设置key的序列化器为字符串序列化器。不做序列化操作,直接使用。
       redisTemplate.setKeySerializer(new StringRedisSerializer());
       // 设置除hash类型数据外,value序列化器。使用基于Jackson实现的序列化器。可以处理Java对象和JSON字符串
       redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
       //设置hash类型数据的field和value序列化器
       redisTemplate.setHashKeySerializer(new StringRedisSerializer());
       redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
       // 设置连接工厂
       redisTemplate.setConnectionFactory(redisConnectionFactory);
       return redisTemplate;
   }



}
