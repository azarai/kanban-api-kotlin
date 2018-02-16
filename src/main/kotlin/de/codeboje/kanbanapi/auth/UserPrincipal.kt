package de.codeboje.kanbanapi.auth

import java.util.ArrayList
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserPrincipal(val user: User) : UserDetails {
	
	override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
			val roles = ArrayList<SimpleGrantedAuthority>()
			roles.add(SimpleGrantedAuthority("user"))
			return roles
	}

	override fun isEnabled(): Boolean {
		return true
	}

	override fun getUsername(): String {
		return user.username
	}

	override fun isCredentialsNonExpired(): Boolean {
		return true
	}

	override fun getPassword(): String? {
		return user.password
	}

	override fun isAccountNonExpired(): Boolean {
		return true
	}

	override fun isAccountNonLocked(): Boolean {
		return true
	}

	val id: Long
		get() {
			return user.id
		}
}