package de.codeboje.kanbanapi.auth

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@Entity
@Table(name = "kuser")
class User: Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var id: Long =0
	
	@Column(length = 50, nullable = false, unique = true)
	@NotEmpty
	@Size(min = 4, max = 50, message = "length must be between {min} and {max} characters")
	var username: String = ""
	
	@Column(length = 100)
	@NotEmpty
//	@get:Size(groups = { UserCreateValidationGroup::class.java }, min = 6, max = 40, message = "length must be between {min} and {max} characters")
	var password: String = ""
}