package de.codeboje.kanbanapi.controller

import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import de.codeboje.kanbanapi.ApiError
import de.codeboje.kanbanapi.MessageCode
import de.codeboje.kanbanapi.model.Board
import de.codeboje.kanbanapi.model.Task
import de.codeboje.kanbanapi.service.TaskService

@RestController
class TaskController {
	@Autowired
	lateinit var service: TaskService

	@GetMapping("/tasks/{boardId}")
	@PreAuthorize("hasPermission(#board, 'read')")
	fun getTasks(@PathVariable("boardId") board: Board?): ResponseEntity<*> {
		if (board == null) {
			return ResponseEntity<ApiError>(ApiError(MessageCode.BOARD_DOES_NOT_EXIST), HttpStatus.BAD_REQUEST)
		}
		return ResponseEntity(service.getTasks(board), HttpStatus.OK)
	}

	@PostMapping("/tasks/{boardId}")
	@PreAuthorize("hasPermission(#board, 'write')")
	fun createTask(@Valid @RequestBody task: Task, @PathVariable("boardId") board: Board?): ResponseEntity<*> {
		if (board == null) {
			return ResponseEntity<ApiError>(ApiError(MessageCode.BOARD_DOES_NOT_EXIST), HttpStatus.BAD_REQUEST)
		}
		val taskDb = service.createTask(task, board)
		return ResponseEntity<Task>(taskDb, HttpStatus.CREATED)
	}

	@PutMapping("/task/{id}")
	@PreAuthorize("hasPermission(#task, 'write')")
	fun updateTask(@PathVariable("id") task: Task?, @Valid @RequestBody taskIn: Task): ResponseEntity<*> {
		if (task == null) {
			return ResponseEntity<ApiError>(ApiError(MessageCode.TASK_DOES_NOT_EXIST), HttpStatus.BAD_REQUEST)
		}
		return ResponseEntity<Task>(service.updateTask(task, taskIn), HttpStatus.OK)
	}

	@DeleteMapping("/task/{id}")
	@PreAuthorize("hasPermission(#task, 'write')")
	fun deleteTask(@PathVariable("id") task: Task) {
		service.deleteTask(task)
	}
}