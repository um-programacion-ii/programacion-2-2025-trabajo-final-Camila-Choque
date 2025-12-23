package org.example.project.config

object ApiConfig {
    val baseUrl: String
        get() = when (Plataforma.name) {
            "Android" -> "http://10.0.2.2:8081" // Emulador
            "iOS" -> "http://localhost:8081" // Simulador iOS
            else -> "http://localhost:8081"
        }
}