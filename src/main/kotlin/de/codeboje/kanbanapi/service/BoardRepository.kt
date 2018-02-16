package de.codeboje.kanbanapi.service

import org.springframework.data.repository.CrudRepository
import de.codeboje.kanbanapi.model.Board

interface BoardRepository : CrudRepository<Board, Long> {
	fun findAllByUser(user: Long?): List<Board>
}