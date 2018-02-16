package de.codeboje.kanbanapi

import java.util.UUID

class InternalApiError(msgCode: MessageCode) : ApiError(msgCode) {
	val uniqueErrorId = UUID.randomUUID().toString().replace("-", "")
}