package de.codeboje.kanbanapi

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty


open class ApiError(msgCode: MessageCode) {
	@JsonIgnore
	private val msgCode: MessageCode

	init {
		this.msgCode = msgCode
	}

	@JsonProperty("code")
	fun getMsgCode(): String? {
		return msgCode.code
	}

	val msg: String?
		@JsonProperty("msg")
		get() {
			return msgCode.msg
		}
}