package de.codeboje.kanbanapi;

import org.springframework.context.annotation.Bean
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession
import org.springframework.session.web.http.HeaderHttpSessionIdResolver
import org.springframework.session.web.http.HttpSessionIdResolver
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.Collections
import org.springframework.context.annotation.Configuration

@EnableJdbcHttpSession
@EnableSpringDataWebSupport
@EnableSwagger2
@Configuration
open class AppConfig {
	@Bean
	open public fun httpSessionIdResolver(): HttpSessionIdResolver {
		return HeaderHttpSessionIdResolver.xAuthToken();
	}
		
	@Bean
	open public fun api() : Docket {
		return Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("de.codeboje.kanbanapi"))
				.paths(PathSelectors.any())
				.build();
	}

	open public fun apiInfo(): ApiInfo {
		return ApiInfo(
				"Kanban API",
				"Kanban API as a backend for testing UIs",
				"v1",
				"Free to use",
				Contact("Jens Boje", "kanbanbackend.com", "info@codeboje.de"),
				"Free to us",
				"Free to use",
				Collections.emptyList());
	}
}
