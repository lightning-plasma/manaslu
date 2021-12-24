package com.archetype.manaslu.repository

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.util.concurrent.*

object ChannelFactory {
    private const val DEFAULT_LOAD_BALANCING_POLICY = "round_robin"
    private const val DEFAULT_KEEP_ALIVE_TIME = 10L
    private const val DEFAULT_KEEP_ALIVE_TIMEOUT = 20L

    fun create(
        host: String,
        port: Int,
        enableSsl: Boolean
    ): ManagedChannel {
        val builder = ManagedChannelBuilder
            .forAddress(host, port)
            .defaultLoadBalancingPolicy(DEFAULT_LOAD_BALANCING_POLICY)
            .keepAliveWithoutCalls(true)
            .keepAliveTime(DEFAULT_KEEP_ALIVE_TIME, TimeUnit.SECONDS)
            .keepAliveTimeout(DEFAULT_KEEP_ALIVE_TIMEOUT, TimeUnit.SECONDS)

        if (enableSsl) builder.useTransportSecurity() else builder.usePlaintext()

        return builder.build()
    }
}
