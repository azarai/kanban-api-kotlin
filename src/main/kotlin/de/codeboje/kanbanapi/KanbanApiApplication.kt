package de.codeboje.kanbanapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KanbanApiApplication 

fun main(args: Array<String>) {
	runApplication<KanbanApiApplication>(*args)
}
