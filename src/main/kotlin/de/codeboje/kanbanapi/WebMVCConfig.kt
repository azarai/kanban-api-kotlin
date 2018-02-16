package de.codeboje.kanbanapi

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMVCConfig : WebMvcConfigurer {

	override fun addCorsMappings(registry: CorsRegistry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.exposedHeaders("x-auth-token")
				.allowedMethods("GET", "POST", "PUT", "DELETE").allowedHeaders("*")
				.allowCredentials(true).maxAge(3600)
	}
}