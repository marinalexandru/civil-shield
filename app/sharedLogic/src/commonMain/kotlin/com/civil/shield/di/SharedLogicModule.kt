package com.civil.shield.di

import com.civil.shield.auth.AuthRepository
import com.civil.shield.core.config.ServerConfig
import com.civil.shield.features.auth.ui.AuthScreenViewModel
import io.ktor.client.HttpClient
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val sharedLogicModule: Module = module {
    single<HttpClient> { AuthRepository.createDefaultHttpClient() }
    single { AuthRepository(httpClient = get(), backendBaseUrl = ServerConfig.BASE_URL) }
    viewModelOf(::AuthScreenViewModel)
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(sharedLogicModule)
    }
