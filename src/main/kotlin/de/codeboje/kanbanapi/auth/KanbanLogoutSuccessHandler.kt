package de.codeboje.kanbanapi.auth

import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler

class KanbanLogoutSuccessHandler : LogoutSuccessHandler {
	
	@Override
	@Throws(IOException::class, ServletException::class)
	override fun onLogoutSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication?) {
		response.setStatus(200)
	}
}