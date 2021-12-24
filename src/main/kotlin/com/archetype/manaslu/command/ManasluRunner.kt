package com.archetype.manaslu.command

import com.archetype.manaslu.service.MountainService
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class ManasluRunner(
    private val mountainService: MountainService
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) = runBlocking {
        val count = mountainService.invoke()
        logger.info("API Call Count: $count")
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }
}
