package de.codeboje.kanbanapi

class ValidationApiError(msgCode: MessageCode, val validationErrors: Collection<ValidationError>) : ApiError(msgCode) {
	
}