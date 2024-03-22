package com.heartsync

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HeartsyncServerApplication

fun main(args: Array<String>) {
    runApplication<HeartsyncServerApplication>(*args)
}
