package de.codeboje.kanbanapi.auth

import java.util.Optional
import javax.transaction.Transactional
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.support.Repositories
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import de.codeboje.kanbanapi.ApiError
import de.codeboje.kanbanapi.MessageCode
import de.codeboje.kanbanapi.model.Board
import de.codeboje.kanbanapi.model.Confirmation
import de.codeboje.kanbanapi.service.TaskService
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam

@RestController
class UserController {
	@Autowired
	lateinit var userRepository: UserRepository
	
	@Autowired
	lateinit var  passwordEncoder: PasswordEncoder
	
	@Autowired
	lateinit var  service: TaskService

	@PostMapping("/login")
	fun login() {
// spring session automatically returns the session token in the header
	}

	@PostMapping("/register")
	fun registerUser(@Validated(UserCreateValidationGroup::class) @RequestBody userIn: User?): ResponseEntity<*> {
		if (userRepository.findByUsername(userIn!!.username) == null) {
			var userDb = User()
			userDb.username = userIn.username
			userDb.password = passwordEncoder.encode(userIn.password)
			userDb = userRepository.save(userDb)
			return ResponseEntity<String>(userDb!!.id.toString(), HttpStatus.CREATED)
		} else {
			return ResponseEntity<ApiError>(ApiError(MessageCode.USER_ALREADY_EXISTS), HttpStatus.UNPROCESSABLE_ENTITY)
		}
	}

	@DeleteMapping("/unregister")
	@Transactional
	fun deleteUser(@Valid @RequestBody @ApiParam(value = "Password of user") confirmation: Confirmation?) {
		val userO = userRepository.findById((SecurityContextHolder.getContext().getAuthentication().getPrincipal() as UserPrincipal).id)
		if (userO.isPresent()) {
			val user = userO.get()
			if (user.password.equals(passwordEncoder.encode(confirmation?.confirm))) {
				service.deleteAllBoards(user)
				userRepository.delete(user)
			}
		}
	}
}