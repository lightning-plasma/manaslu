package com.archetype.manaslu.service

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "manaslu.service")
data class ServiceConfig(
    val parallels: Int,
    val executionTime: Long,
)
