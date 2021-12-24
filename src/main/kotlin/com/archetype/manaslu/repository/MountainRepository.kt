package com.archetype.manaslu.repository

import com.archetype.makalu.proto.hike.MountainRequest
import com.archetype.makalu.proto.hike.MountainServiceGrpcKt
import com.archetype.manaslu.entity.HighestPoint
import com.archetype.manaslu.entity.Mountain
import com.archetype.manaslu.repository.refresh.provider.RefreshableNameResolverProvider
import io.grpc.NameResolverRegistry
import mu.KotlinLogging
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Repository
import java.util.concurrent.*

@Repository
@EnableConfigurationProperties(value = [GatewayConfig::class])
class MountainRepository(
    config: GatewayConfig,
    provider: RefreshableNameResolverProvider?
) {
    init {
        // custom providerを NameResolverRegistryにセットする
        if (provider != null) {
            logger.info("set custom provider")
            NameResolverRegistry.getDefaultRegistry().register(provider)
        }
    }

    private val channel by lazy {
        ChannelFactory.create(
            config.host,
            config.port,
            config.enableSsl
        )
    }

    suspend fun getMountainDetail(name: String): Mountain {
        val stub = MountainServiceGrpcKt.MountainServiceCoroutineStub(channel)
            .withDeadlineAfter(DEADLINE_AFTER, TimeUnit.MILLISECONDS)
            .withCompression(DEFAULT_COMPRESSOR)

        val req = MountainRequest.newBuilder()
            .setName(name)
            .build()

        val res = stub.getMountainDetail(req)

        return Mountain(
            name = res.name,
            country = res.country,
            mountainSystem = res.mountainSystem,
            highestPoint = HighestPoint(
                elevation = res.highestPoint.elevation,
                prominence = res.highestPoint.prominence,
                isolation = res.highestPoint.isolation,
                listing = res.highestPoint.listingList,
                coordinate = res.highestPoint.coordinates
            )
        )
    }

    companion object {
        private const val DEADLINE_AFTER = 1000L
        private const val DEFAULT_COMPRESSOR = "gzip"
        private val logger = KotlinLogging.logger { }
    }
}
