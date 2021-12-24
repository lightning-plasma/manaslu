package com.archetype.manaslu.service

import com.archetype.manaslu.repository.MountainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mu.KotlinLogging
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.*

@Service
@EnableConfigurationProperties(value = [ServiceConfig::class])
class MountainService(
    private val mountainRepository: MountainRepository,
    private val config: ServiceConfig
) {
    suspend fun invoke() = coroutineScope {
        val start = System.currentTimeMillis()
        logger.info("Api call start. configuration=$config")

        val callCount = AtomicInteger()

        val jobs = mutableListOf<Job>()
        repeat(config.parallels) {
            jobs += launch {
                while (System.currentTimeMillis() - start < config.executionTime) {
                    val m = withContext(Dispatchers.Default) {
                        try {
                            mountainRepository.getMountainDetail("makalu")
                        } catch (e: Throwable) {
                            logger.warn(e.message)
                        }
                    }

                    callCount.addAndGet(1)

                    if (logger.isDebugEnabled) {
                        logger.debug { m }
                        logger.debug { System.currentTimeMillis() - start }
                    }
                }
            }
        }

        jobs.joinAll()
        logger.info("Api Call Count=${callCount.get()}")
    }

    companion object {
        private val logger = KotlinLogging.logger { }
    }
}
