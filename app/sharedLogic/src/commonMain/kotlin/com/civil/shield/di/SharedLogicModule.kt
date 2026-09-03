package com.civil.shield.di

import com.civil.shield.auth.AuthApiService
import com.civil.shield.auth.AuthApiServiceImpl
import com.civil.shield.core.config.ServerConfig
import com.civil.shield.core.network.HttpClientFactory
import io.ktor.client.HttpClient
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.module

@Module
@ComponentScan("com.civil.shield")
class SharedLogicModule {
    @Single
    fun httpClient(): HttpClient = HttpClientFactory.create()

    @Single
    fun authApiService(httpClient: HttpClient): AuthApiService =
        AuthApiServiceImpl(httpClient = httpClient, backendBaseUrl = ServerConfig.BASE_URL)
}

val sharedLogicModule = SharedLogicModule().module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(sharedLogicModule)
    }
