package com.taotao.service;

public interface JedisClient {
	/*创建string的redis缓存*/
	String set(String key, String value);
	/*通过键得到redis缓存*/
	String get(String key);
	/*判断该key是否存在*/
	Boolean exists(String key);
	/*设置该key的过期时间*/
	Long expire(String key, int seconds);
	/*获取key的剩余存活时间*/
	Long ttl(String key);
	/*设置key的自增长*/
	Long incr(String key);
	/*创建hash散列数据*/
	Long hset(String key, String field, String value);
	/*获取hash散列数据*/
	String hget(String key, String field);
	/*删除hash散列中的key*/
	Long hdel(String key, String... field);
	/*删除string的key*/
	Long del(String key);
}
