package com.civil.shield.modules.system.service

import com.civil.shield.sayHello
import org.springframework.stereotype.Service

@Service
class SystemServiceImpl : SystemService {
    override fun getGreeting(): String {
        return sayHello("CivilShield Backend")
    }
}
