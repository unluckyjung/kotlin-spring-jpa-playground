# Mysql

### Setting

```console
$ docker-compose up -d

$ docker exec -it mysql /bin/bash
$ mysql -u root -p
```

### Command

```console
show databases
use unluckyjung
```

### Spring Connection

```yml
-- applicaton.yml

spring:
  datasource:
    driver-class-name: org.mariadb.jdbc.Driver
    url: jdbc:mysql://localhost:13306/unluckyjung
    username: root
    password: asdf
```

<img width="818" alt="image" src="https://user-images.githubusercontent.com/43930419/212459888-baa0fb9c-3429-43c9-af76-a95c80458023.png">

# Redis

### Setting
```console
$ docker pull redis

$ docker-compose -d

$ docker exec -it redis redis-cli
```

### Command

```console
$ keys *
$ type <key>
$ flushall

# [string]
$ get <key>
$ mget <key>
$ ttl <key>
$ del <key>

# [set]
$ smembers <key>
$ srem <key>

# [hash]
$ hkeys <key>
$ hget <key> <hashKey>
$ hdel <key> <hashKey>
```

### Spring Connection

```gradle
// build.gradle.kts

implementation("org.springframework.boot:spring-boot-starter-data-redis")
```

```yml
-- applicaton.yml

spring:
  redis:
    host: localhost
    port: 6379
```

```kotlin
@EnableCaching
@SpringBootApplication
class KopringApplication

```

```kotlin
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class RedisConfig(
    @Value("\${spring.redis.host}")
    val host: String,

    @Value("\${spring.redis.port}")
    val port: Int,
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<*, *> {
        return RedisTemplate<Any, Any>().apply {
            this.setConnectionFactory(redisConnectionFactory())
        }
    }
}
```
