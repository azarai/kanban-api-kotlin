package de.codeboje.kanbanapi.auth

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AppUserDetailsService : UserDetailsService {
	@Autowired
	lateinit private var userRepository: UserRepository

	@Override
	override fun loadUserByUsername(username: String): UserDetails {
		val user = userRepository.findByUsername(username)
		if (user == null) {
			throw UsernameNotFoundException(username)
		}
		return UserPrincipal(user)
	}
}