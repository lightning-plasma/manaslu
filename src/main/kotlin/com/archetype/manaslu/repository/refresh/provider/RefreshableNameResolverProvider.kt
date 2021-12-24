package com.archetype.manaslu.repository.refresh.provider

import io.grpc.NameResolver
import io.grpc.NameResolverProvider
import io.grpc.internal.DnsNameResolverProvider
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import java.net.URI
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

// https://github.com/grpc/grpc-java/issues/4159
@Component
@ConditionalOnProperty(
    prefix = "gateway.env.clientlb",
    name = ["enabled"],
    havingValue = "true",
    matchIfMissing = false
)
class RefreshableNameResolverProvider(
    private val _resolvers: MutableList<NameResolver> = mutableListOf(),
    private val _delegate: DnsNameResolverProvider = DnsNameResolverProvider()
) : NameResolverProvider() {
    // mutableListを変更する箇所をlockしてThread safeを担保する
    private val lock = ReentrantLock()

    override fun newNameResolver(targetUri: URI, args: NameResolver.Args): NameResolver? {
        val r: NameResolver? = _delegate.newNameResolver(targetUri, args)

        if (r != null) {
            logger.debug("grpc server resolver set. uri=$targetUri")
            lock.withLock {
                _resolvers += r
            }
        }

        return r
    }

    override fun getDefaultScheme(): String {
        return _delegate.defaultScheme
    }

    override fun isAvailable(): Boolean {
        return true
    }

    override fun priority(): Int {
        return PRIORITY
    }

    fun refresh() {
        _resolvers.forEach {
            logger.debug("refresh ${it.serviceAuthority}")

            try {
                it.refresh()
            } catch (e: Throwable) {
                logger.info(
                    "refresh failure. service={}, message={}",
                    it.serviceAuthority,
                    e.message
                )
            }
        }
    }

    companion object {
        private val logger = KotlinLogging.logger {}

        // https://github.com/grpc/grpc-java/blob/master/core/src/main/java/io/grpc/internal/DnsNameResolverProvider.java
        // DnsNameResolverProviderよりもPRIORITYを高く設定する
        private const val PRIORITY = 9
    }
}
