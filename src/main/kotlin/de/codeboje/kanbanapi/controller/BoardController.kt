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
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import de.codeboje.kanbanapi.ApiError
import de.codeboje.kanbanapi.MessageCode
import de.codeboje.kanbanapi.model.Board
import de.codeboje.kanbanapi.model.Confirmation
import de.codeboje.kanbanapi.service.TaskService
import io.swagger.annotations.ApiParam

@RestController
class BoardController {
	@Autowired
	lateinit var service: TaskService

	@GetMapping("/boards")
	fun getBoards(): List<Board> {
		return service.getBaords()
	}

	@PostMapping("/boards")
	@ResponseStatus(code = HttpStatus.CREATED)
	fun createBoard(@Valid @RequestBody board: Board): Board? {
		return service.createBoard(board)
	}

	@PutMapping("/board/{id}")
	@PreAuthorize("hasPermission(#board, 'write')")
	fun updateBoard(@PathVariable("id") board: Board?, @Valid @RequestBody boardIn: Board): ResponseEntity<*> {
		if (board == null) {
			return ResponseEntity<ApiError>(ApiError(MessageCode.BOARD_DOES_NOT_EXIST), HttpStatus.BAD_REQUEST)
		}
		return ResponseEntity<Board>(service.updateBoard(board, boardIn), HttpStatus.OK)
	}

	@DeleteMapping("/board/{id}")
	@PreAuthorize("hasPermission(#board, 'write')")
	fun deleteBoard(@PathVariable("id") board: Board?, @Valid @RequestBody @ApiParam(value = "name of the board") confirmation: Confirmation?) {
		if (board != null) {
			if (board.name.equals(confirmation?.confirm)) {
				service.deleteBoard(board)
			}
		}
	}
}