package de.codeboje.kanbanapi

import java.util.ArrayList

data class ValidationError(val field: String, val msg : ArrayList<String> = ArrayList<String>()) {

}