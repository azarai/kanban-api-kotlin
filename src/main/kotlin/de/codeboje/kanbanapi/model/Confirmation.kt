package de.codeboje.kanbanapi.model

import javax.validation.constraints.NotEmpty

data class Confirmation(@NotEmpty
						var confirm: String) {

}