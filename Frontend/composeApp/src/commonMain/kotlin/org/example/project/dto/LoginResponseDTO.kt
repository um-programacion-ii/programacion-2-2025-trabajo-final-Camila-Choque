package org.example.project.dto
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDTO (
    val jwt: String,
    val sessionToken: String,
    val sessionId: Long

)