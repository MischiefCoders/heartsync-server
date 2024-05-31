package com.heartsync.configuration

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.RedisTemplate


@SpringBootTest
class RedisTemplateTest(
    @Autowired val redisTemplate: RedisTemplate<String, Any>
) {

    // FIXME : 테스트용 redis 서버 필요 or 내장 redis 적용
    @BeforeEach
    fun setUp() {
        val keys = redisTemplate.keys("*")
        if (keys.isNotEmpty()) {
            redisTemplate.delete(keys)
        }
    }

    @AfterEach
    fun tearDown() {
        val keys = redisTemplate.keys("*")
        if (keys.isNotEmpty()) {
            redisTemplate.delete(keys)
        }
    }

    @Test
    fun testString() {
        // given
        val valueOperations = redisTemplate.opsForValue()
        val key = "stringKey"

        // when
        valueOperations[key] = "hello"

        // then
        val value = valueOperations[key]
        assertThat(value).isEqualTo("hello")
    }

    @Test
    fun testList() {
        // given
        val listOperations = redisTemplate.opsForList()
        val key = "listKey"

        // when
        listOperations.leftPush(key, 1)
        listOperations.leftPush(key, 2)
        listOperations.leftPush(key, 3)
        listOperations.leftPush(key, 1)

        // then
        assertThat(listOperations.size(key)).isEqualTo(4)
    }

    @Test
    fun testSet() {
        // given
        val setOperations = redisTemplate.opsForSet()
        val key = "setKey"

        // when
        setOperations.add(key, "one", "two", "three", "one")

        // then
        val members = setOperations.members(key)

        assertThat(members).containsOnly("one", "two", "three")
        assertThat(members).hasSize(3)
    }

    @Test
    fun testHash() {
        // given
        val hashOperations = redisTemplate.opsForHash<String, Any>()
        val key = "hashKey"

        // when
        hashOperations.put(key, "k1", "v1")

        // then
        val value = hashOperations.get(key, "k1")

        assertThat(value).isEqualTo("v1")
    }
}