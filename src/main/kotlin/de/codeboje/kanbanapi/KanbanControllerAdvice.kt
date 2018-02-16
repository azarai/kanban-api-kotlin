package de.codeboje.kanbanapi

import javax.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

//@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
class KanbanControllerAdvice {
	private val LOGGER = LoggerFactory.getLogger(KanbanControllerAdvice::class.java)
	
	@ExceptionHandler(Exception::class)
	fun defaultException(request: HttpServletRequest, e: Exception): ResponseEntity<ApiError> {
		val msg = InternalApiError(MessageCode.TECHNICAL_ERROR)
		LOGGER!!.error("Server error with uniqueErrorId={}, path={}", msg.uniqueErrorId, request.getRequestURI(), e)
		return ResponseEntity<ApiError>(msg, HttpStatus.INTERNAL_SERVER_ERROR)
	}

	@ExceptionHandler(AccessDeniedException::class)
	fun handleException(exception: AccessDeniedException): ResponseEntity<ApiError> {
		return ResponseEntity<ApiError>(ApiError(MessageCode.ACCESS_DENIED), HttpStatus.FORBIDDEN)
	}
//	@ExceptionHandler(AuthenticationException.class)
//	public ResponseEntity<ApiError> handleException(AuthenticationException exception) {
//		return new ResponseEntity<ApiError>(new ApiError(MessageCode.ACCESS_DENIED), HttpStatus.UNAUTHORIZED);
//	}

	@ExceptionHandler(HttpMessageNotReadableException::class)
	fun handleException(exception: HttpMessageNotReadableException): ResponseEntity<ApiError> {
		return ResponseEntity<ApiError>(ApiError(MessageCode.REQUEST_PARSING_ERROR), HttpStatus.BAD_REQUEST)
	}

	@ExceptionHandler(MethodArgumentNotValidException::class)
	fun handleException(exception: MethodArgumentNotValidException): ResponseEntity<ApiError> {
		val fieldErrors = exception.getBindingResult().getFieldErrors()
		val tempErrors = HashMap<String, ValidationError>()
		for (fieldError in fieldErrors!!) {
			if (!tempErrors.containsKey(fieldError.getField())) {
				tempErrors.put(fieldError.getField(), ValidationError(fieldError.getField()))
			}
			tempErrors.get(fieldError.getField())!!.msg.add(fieldError.getDefaultMessage()!!)
		}
		val msg = ValidationApiError(MessageCode.USER_INVALID,tempErrors.values)
		return ResponseEntity<ApiError>(msg, HttpStatus.UNPROCESSABLE_ENTITY)
	}
}