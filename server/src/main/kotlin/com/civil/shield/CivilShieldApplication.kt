package com.civil.shield

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class CivilShieldApplication

fun main(args: Array<String>) {
    runApplication<CivilShieldApplication>(*args)
}
