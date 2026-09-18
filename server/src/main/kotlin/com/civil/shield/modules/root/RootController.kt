package com.civil.shield.modules.root

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RootController {

    @GetMapping("/", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun getRoot(): ResponseEntity<String> {
        return ResponseEntity.ok(RootController::class.java.name)
    }
}
