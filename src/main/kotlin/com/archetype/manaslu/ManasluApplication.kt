package com.archetype.manaslu

import com.archetype.manaslu.core.DnsCacheTtl
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ManasluApplication {
    companion object {
        init {
            // JVM DNS Cacheを無効化する
            // Spring Bootが起動して、かつBeanが生成されないタイミングに実行させるためここで呼び出す
            // https://www.baeldung.com/kotlin/static-initialization-block
            DnsCacheTtl.setup()
        }
    }
}

fun main(args: Array<String>) {
	runApplication<ManasluApplication>(*args)
}
