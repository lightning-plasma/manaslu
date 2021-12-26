package com.archetype.manaslu.service

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.time.Duration

@ConstructorBinding
@ConfigurationProperties(prefix = "manaslu.service")
data class ServiceConfig(
    val parallels: Int,
    val executionTime: Duration,
) {
    val executionTimeMilliseconds = executionTime.seconds * 1000L
}
