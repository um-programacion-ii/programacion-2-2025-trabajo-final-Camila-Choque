package org.example.project.dto
import kotlinx.serialization.Serializable

@Serializable
data class RegistrarDTO(
    val login: String,
    val password: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val langKey: String,
)
