package de.codeboje.kanbanapi

enum class MessageCode private constructor(code: String, msg: String) {
	TECHNICAL_ERROR("001", "technical error"),
	REQUEST_PARSING_ERROR("002", "request could not be parsed"),
	ACCESS_DENIED("003", "access denied"),
	
	USER_ALREADY_EXISTS("020", "user already exists"),
	USER_INVALID("021", "user is not valid, please check validation errors"),
	
	BOARD_DOES_NOT_EXIST("040", "board does not exist"),
	TASK_DOES_NOT_EXIST("060", "task does not exist");

	val code: String
	val msg: String

	init {
		this.code = code
		this.msg = msg
	}
}