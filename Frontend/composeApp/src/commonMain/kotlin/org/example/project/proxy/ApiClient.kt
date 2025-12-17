package org.example.project.proxy
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.dto.EventoDTO
import org.example.project.dto.LoginDTO

object ApiClient {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
        }

    }

    suspend fun login(request: LoginDTO): String {
        val response: String =
            client.post("http://localhost:8081/api/authenticate") {
                contentType(io.ktor.http.ContentType.Application.Json)
                setBody(request)
            }.body()

        return response
    }

    suspend fun obtenerEventos(): List<EventoDTO>{
        return client.get("http://localhost:8081/api/catedra/eventos").body()
    }

    suspend fun obtenerEventosPorId(id: Long): EventoDTO {
        return client.get("http://localhost:8081/api/catedra/eventos/$id").body()
    }


}