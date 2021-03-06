package de.codeboje.kanbanapi.auth

import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
	fun findByUsername(username: String?): User?
}