package com.civil.shield.modules.system.controller

import com.civil.shield.modules.system.service.SystemService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SystemController(
    private val systemService: SystemService
) {

    @GetMapping("/", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun getGreeting(): ResponseEntity<String> {
        return ResponseEntity.ok(systemService.getGreeting())
    }
}
