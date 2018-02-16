package de.codeboje.kanbanapi.service

import javax.transaction.Transactional
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import de.codeboje.kanbanapi.auth.User
import de.codeboje.kanbanapi.auth.UserPrincipal
import de.codeboje.kanbanapi.model.Board
import de.codeboje.kanbanapi.model.Task

@Service
class TaskService {
	@Autowired
	lateinit var repository: TaskRepository

	@Autowired
	lateinit var boardRepository: BoardRepository

	fun getBaords(): List<Board> {
		return boardRepository.findAllByUser(loggedInUserId)
	}

	fun createBoard(board: Board): Board {
		board.id = null
		board.user = loggedInUserId
		return boardRepository.save(board)
	}

	fun getTasks(board: Board): List<Task> {
		return repository.findAllByBoard(board)
	}

	fun createTask(task: Task, board: Board): Task? {
		task.id = null
		task.board = board
		return repository.save(task)
	}

	fun updateTask(taskDb: Task, taskIn: Task): Task? {
		taskDb.category = taskIn.category
		taskDb.content = taskIn.content
		taskDb.lane = taskIn.lane
		return repository.save(taskDb)
	}

	fun deleteTask(task: Task) {
		repository.delete(task)
	}

	private val loggedInUserId: Long?
		get() {
			return (SecurityContextHolder.getContext().getAuthentication().getPrincipal() as UserPrincipal).id
		}

	fun updateBoard(board: Board, @Valid boardIn: Board): Board {
		board.name = boardIn.name
		return boardRepository.save(board)
	}

	@Transactional
	fun deleteBoard(board: Board) {
		repository.deleteAllByBoard(board)
		boardRepository.delete(board)
	}

	fun deleteAllBoards(user: User) {
		val boards = boardRepository.findAllByUser(user.id)
		boards.forEach({ this.deleteBoard(it) })
	}
}