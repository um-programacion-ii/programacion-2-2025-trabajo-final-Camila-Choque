package org.example.project.proxy
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.example.project.config.ApiConfig
import org.example.project.dto.EventoDTO
import org.example.project.dto.LoginDTO
import org.example.project.dto.LoginResponseDTO

object ApiClient {
    private var jwtToken: String? = null
    private var sessionToken: String? = null  // ✅ Agrega esto

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                prettyPrint = true
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 30000
            connectTimeoutMillis = 15000
            socketTimeoutMillis = 15000
        }

        expectSuccess = false

        defaultRequest {
            url(ApiConfig.baseUrl)
            contentType(ContentType.Application.Json)
            println("🏗️ Base URL configurada: ${ApiConfig.baseUrl}")  // ← Log esto
        }
    }

    fun setTokens(jwt: String, session: String) {
        jwtToken = jwt
        sessionToken = session
        println("💾 JWT guardado: ${jwt.take(30)}...")
        println("💾 Session Token guardado: $session")
    }

    fun clearTokens() {
        jwtToken = null
        sessionToken = null
    }

    fun getToken(): String? = jwtToken
    fun getSessionToken(): String? = sessionToken

    // ✅ Helper para agregar AMBOS tokens
    private fun HttpRequestBuilder.addAuth() {
        jwtToken?.let { token ->
            header("Authorization", "Bearer $token")
            println("🔑 Authorization header agregado")
        }
        sessionToken?.let { session ->
            header("X-SESSION-TOKEN", session)
            println("🎫 X-SESSION-TOKEN header agregado")
        }

        if (jwtToken == null || sessionToken == null) {
            println("⚠️ Faltan tokens! JWT: ${jwtToken != null}, Session: ${sessionToken != null}")
        }
    }

    suspend fun login(request: LoginDTO): Result<String> {
        return try {
            println("🚀 Intentando login a: ${ApiConfig.baseUrl}/api/authenticate")
            println("📦 Request body: ${Json.encodeToString(request)}")

            val response: HttpResponse = client.post {
                url("${ApiConfig.baseUrl}/api/authenticate")
                contentType(ContentType.Application.Json)
                setBody(request)
            }

            println("📡 Status code: ${response.status.value}")

            if (response.status.value in 200..299) {
                val loginResponse: LoginResponseDTO = response.body()

                // ✅ Guarda AMBOS tokens
                setTokens(loginResponse.jwt, loginResponse.sessionToken)

                println("✅ Login exitoso!")
                println("📝 JWT guardado: ${loginResponse.jwt.take(30)}...")
                println("📝 Session guardado: ${loginResponse.sessionToken}")

                Result.success(loginResponse.jwt)
            } else {
                val errorBody = response.bodyAsText()
                println("❌ Error: ${response.status} - $errorBody")
                Result.failure(Exception("Error ${response.status.value}: $errorBody"))
            }
        } catch (e: Exception) {
            println("💥 Excepción login: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getEvents(): Result<List<EventoDTO>> {
        return try {
            println("🚀 GET /v1/service/eventos")
            println("🔍 Tokens disponibles - JWT: ${jwtToken?.take(30)}, Session: $sessionToken")

            val response: HttpResponse = client.get("/api/v1/service/eventos") {
                addAuth()  // ✅ Agrega AMBOS tokens
            }

            println("📡 Status code: ${response.status.value}")
            println("📋 Headers enviados: ${response.request.headers.entries()}")

            if (response.status.value in 200..299) {
                val events: List<EventoDTO> = response.body()
                println("✅ ${events.size} eventos cargados")
                Result.success(events)
            } else {
                val errorBody = response.bodyAsText()
                println("❌ Error ${response.status.value}: $errorBody")
                Result.failure(Exception("Error ${response.status.value}: $errorBody"))
            }
        } catch (e: Exception) {
            println("💥 Excepción getEvents: ${e.message}")
            e.printStackTrace()
            Result.failure(e)
        }
    }
}

