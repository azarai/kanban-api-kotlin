package de.codeboje.kanbanapi.service

import org.springframework.data.repository.CrudRepository
import de.codeboje.kanbanapi.model.Board
import de.codeboje.kanbanapi.model.Task

interface TaskRepository : CrudRepository<Task, Long> {
	fun findAllByBoard(board: Board?): List<Task>
	fun deleteAllByBoard(board: Board?)
}