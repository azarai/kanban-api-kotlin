package de.codeboje.kanbanapi

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import de.codeboje.kanbanapi.auth.AppUserDetailsService
import de.codeboje.kanbanapi.auth.KanbanLogoutSuccessHandler
import de.codeboje.kanbanapi.auth.RestAuthenticationEntryPoint

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
	
	@Autowired
	lateinit private var appUserDetailsService: AppUserDetailsService
	
	@Autowired
	lateinit private var restAuthenticationEntryPoint: RestAuthenticationEntryPoint

	@Override
	@Throws(Exception::class)
	override protected fun configure(auth: AuthenticationManagerBuilder) {
		auth.userDetailsService(appUserDetailsService).passwordEncoder(encoder())
	}

	@Override
	@Throws(Exception::class)
	override fun configure(web: WebSecurity) {
		web.ignoring().antMatchers("/h2-console/**", "/v2/api-docs", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**")
	}

	@Override
	@Throws(Exception::class)
	override protected fun configure(http: HttpSecurity) {
		http.csrf().disable()
				.cors().and()
				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
//		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authorizeRequests()
				.antMatchers("/register").permitAll()
				.anyRequest().authenticated()
				.and().headers().frameOptions().disable()
				.and().httpBasic().and().logout().logoutSuccessHandler(KanbanLogoutSuccessHandler())
	}

	@Bean
	fun encoder(): PasswordEncoder {
		return BCryptPasswordEncoder()
	}
}