package de.codeboje.kanbanapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@Entity
class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var id: Long? = null
	
	@Column(length = 100)
	@NotEmpty
	@Size(min = 6, max = 100)
	var name: String? = null
	
	@Column(name = "usercol")
	@JsonIgnore
	var user: Long? = null
}