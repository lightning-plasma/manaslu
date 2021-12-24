package com.archetype.manaslu.repository

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "gateway.mountain")
data class GatewayConfig(
    val host: String,
    val port: Int,
    val enableSsl: Boolean
)
