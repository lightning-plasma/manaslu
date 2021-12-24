package com.archetype.manaslu.command

import com.archetype.manaslu.entity.NameResolverRefreshTimer
import com.archetype.manaslu.service.MountainService
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class ManasluRunner(
    private val mountainService: MountainService,
    private val timer: NameResolverRefreshTimer?
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) = runBlocking {
        try {
            val start = System.currentTimeMillis()
            logger.info { "start batch application" }

            timer?.let {
                logger.info("refresh timer has started")
            }

            mountainService.invoke()
            logger.info { "end batch application. elapsed=${System.currentTimeMillis() - start}" }
        } finally {
            timer?.let {
                logger.info("cancel refresh timer task")
                it.cancel()
            }
        }
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}
