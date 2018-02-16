package de.codeboje.kanbanapi.auth

import java.io.Serializable
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import de.codeboje.kanbanapi.model.Board
import de.codeboje.kanbanapi.model.Task

@Component
class KanbanPermissionEvaluator : PermissionEvaluator {
	@Override
	override fun hasPermission(authentication: Authentication, target: Any?, permission: Any?): Boolean {
		val loggedInUserId = (authentication.getPrincipal() as UserPrincipal).id
		if (target == null) {
			return true
		} else if (target is Board) {
			if (loggedInUserId.equals(target.user)) {
				return true
			}
		} else if (target is Task) {
			if (loggedInUserId.equals(target.board!!.user)) {
				return true
			}
		}
		return false
	}

	@Override
	override fun hasPermission(authentication: Authentication, targetId: Serializable?,
					  targetType: String, permission: Any?): Boolean {
		return false
	}
}