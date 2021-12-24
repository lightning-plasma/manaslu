package com.archetype.manaslu.core

import mu.KotlinLogging

object DnsCacheTtl {
    private val logger = KotlinLogging.logger {}

    // Default値は Security Managerを設定する場合無限. 設定しない場合は 30 sec になる
    // https://docs.aws.amazon.com/ja_jp/neptune/latest/userguide/best-practices-gremlin-java-disable-dns-caching.html
    fun setup() {
        java.security.Security.setProperty("networkaddress.cache.ttl", "0")
        logger.debug("DNS Cache time setting is complete")
    }
}
