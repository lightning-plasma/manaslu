package com.archetype.manaslu.repository.refresh.timer

import com.archetype.manaslu.entity.NameResolverRefreshTimer
import com.archetype.manaslu.repository.refresh.provider.RefreshableNameResolverProvider
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.concurrent.timerTask

@ConditionalOnProperty(
    prefix = "gateway.env.clientlb",
    name = ["enabled"],
    havingValue = "true",
    matchIfMissing = false
)
@Configuration(proxyBeanMethods = false)
class TimerTaskConfig(
    private val provider: RefreshableNameResolverProvider,
) {
    @Bean
    fun timer(): NameResolverRefreshTimer {
        val task = timerTask {
            provider.refresh()
        }

        val timer = NameResolverRefreshTimer(TIMER, true)
        timer.schedule(task, INITIAL_DELAY, FIXED_DELAY)

        return timer
    }

    companion object {
        private const val TIMER = "NameResolverRefreshTimer"
        private const val INITIAL_DELAY = 120L
        private const val FIXED_DELAY = 300L
    }
}
