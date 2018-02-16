package de.codeboje.kanbanapi.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.validation.constraints.Size

@Entity
class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	var id: Long? = null
	
	@Column(length = 2048)
	var content: String? = null
	
	@Column(length = 10)
	@Size(min = 0, max = 10)
	var category: String? = null
	
	@Column(length = 10)
	@Enumerated(EnumType.STRING)
	var lane: Lane? = null
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	@JsonIgnore
	var board: Board? = null
}