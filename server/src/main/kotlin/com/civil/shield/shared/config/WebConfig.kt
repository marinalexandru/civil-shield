package com.civil.shield.shared.config

import kotlinx.serialization.json.Json
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.KotlinSerializationJsonHttpMessageConverter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    override fun extendMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val json = Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
            isLenient = true
        }
        val stringConverterIndex = converters.indexOfFirst {
            it.javaClass.simpleName.contains("String")
        }
        val kotlinJsonConverter = KotlinSerializationJsonHttpMessageConverter(json)
        if (stringConverterIndex >= 0) {
            converters.add(stringConverterIndex + 1, kotlinJsonConverter)
        } else {
            converters.add(kotlinJsonConverter)
        }
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
    }
}
