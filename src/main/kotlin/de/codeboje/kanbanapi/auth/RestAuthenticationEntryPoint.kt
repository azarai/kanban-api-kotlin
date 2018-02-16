package de.codeboje.kanbanapi.auth

import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import com.fasterxml.jackson.databind.ObjectMapper
import de.codeboje.kanbanapi.ApiError
import de.codeboje.kanbanapi.MessageCode

@Component
class RestAuthenticationEntryPoint : AuthenticationEntryPoint {
	@Autowired
	lateinit var objetMapper: ObjectMapper

	@Override
	@Throws(IOException::class)
	override fun commence(request: HttpServletRequest, response: HttpServletResponse,
				 authException: AuthenticationException) {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED)
		response.setContentType("application/json")
		response.setCharacterEncoding("UTF-8")
		response.getWriter().write(objetMapper.writeValueAsString(ApiError(MessageCode.ACCESS_DENIED)))
	}
}