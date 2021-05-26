package com.example.iptrack.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.CacheErrorHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.converter.ByteArrayHttpMessageConverter
import org.springframework.web.client.RestTemplate
import redis.clients.jedis.JedisPoolConfig
import java.time.Duration

@Configuration
@EnableCaching
class CacheConfiguration : CachingConfigurerSupport() {

    @Value("\${REDIS_HOST}")
    private val redisHost: String = ""

    @Value("\${REDIS_PORT}")
    private val redisPort: Int = 0

    private val maxConnections: Int = 10

    private val minIdle: Int = 2

    private val maxIdle: Int = 4

    private val connectionTimeOut: Long = 1000

    private val defaultExpireTime: Long = 3000

    private val redisCachePrefix: String = "ip-track:"

    private val redisMaxWait: Long = 5000

    private val redisMinEvictableIdleTimeMillis: Long = 300000

    fun jedisPoolConfig(): JedisPoolConfig {

        val poolConfiguration = JedisPoolConfig()

        poolConfiguration.maxTotal = maxConnections
        poolConfiguration.minIdle = minIdle
        poolConfiguration.maxIdle = maxIdle
        poolConfiguration.maxWaitMillis = redisMaxWait
        poolConfiguration.minEvictableIdleTimeMillis = redisMinEvictableIdleTimeMillis
        poolConfiguration.blockWhenExhausted = true

        return poolConfiguration
    }

    fun redisConnectionFactory(): RedisConnectionFactory {

        val jedisClientConfiguration = JedisClientConfiguration.builder()
            .connectTimeout(Duration.ofSeconds(connectionTimeOut))
            .usePooling()
            .poolConfig(this.jedisPoolConfig())
            .build()

        return JedisConnectionFactory(RedisStandaloneConfiguration(redisHost, redisPort), jedisClientConfiguration)
    }

    fun redisCacheConfiguration(): RedisCacheConfiguration {

        val serializationPair = RedisSerializationContext.SerializationPair.fromSerializer(
            JdkSerializationRedisSerializer()
        )

        return RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofSeconds(defaultExpireTime))
            .serializeValuesWith(serializationPair)
            .disableCachingNullValues()
            .prefixKeysWith(redisCachePrefix)
    }

    @Bean
    @Primary
    override fun cacheManager(): CacheManager {
        return RedisCacheManager.builder(this.redisConnectionFactory())
            .cacheDefaults(this.redisCacheConfiguration())
            .build()
    }

    fun getCacheManagerByTtl(ttl: Long): RedisCacheManager {
        val configuration = this.redisCacheConfiguration().entryTtl(Duration.ofSeconds(ttl))

        return RedisCacheManager.builder(this.redisConnectionFactory())
            .cacheDefaults(configuration)
            .build()
    }

    @Bean
    fun cacheManager1Hour(): CacheManager {
        return getCacheManagerByTtl(5 * 3600L)
    }

    @Bean
    @Primary
    fun redisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(redisConnectionFactory())
        val serializerKey = JdkSerializationRedisSerializer()
        template.keySerializer = serializerKey

        return template
    }

    @Bean(name = ["redisTemplateStringSerializer"])
    fun redisTemplateStringSerializer(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.setConnectionFactory(redisConnectionFactory())
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = StringRedisSerializer()

        return template
    }
}
