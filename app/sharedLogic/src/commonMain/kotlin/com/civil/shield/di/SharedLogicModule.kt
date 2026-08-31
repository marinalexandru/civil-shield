package com.civil.shield.di

import com.civil.shield.auth.AuthApiService
import com.civil.shield.auth.AuthApiServiceImpl
import com.civil.shield.auth.AuthRepository
import com.civil.shield.core.config.ServerConfig
import com.civil.shield.core.network.HttpClientFactory
import com.civil.shield.features.auth.ui.AuthScreenViewModel
import io.ktor.client.HttpClient
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val sharedLogicModule: Module = module {
    single<HttpClient> { HttpClientFactory.create() }
    single<AuthApiService> { AuthApiServiceImpl(httpClient = get(), backendBaseUrl = ServerConfig.BASE_URL) }
    single { AuthRepository(authApiService = get()) }
    viewModelOf(::AuthScreenViewModel)
}

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(sharedLogicModule)
    }
